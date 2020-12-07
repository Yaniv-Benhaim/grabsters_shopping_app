package tech.gamedev.bolt.ui.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stripe.android.*
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethod
import com.stripe.android.view.BillingAddressFields
import kotlinx.android.synthetic.main.activity_payment.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.repositories.FirebaseRepo
import tech.gamedev.bolt.stripe.FirebaseEphemeralKeyProvider
import tech.gamedev.bolt.ui.adapters.CartAdapter

class PaymentActivity : AppCompatActivity() {

    private val firebaseRepo = FirebaseRepo()
    private val currentUser = firebaseRepo.getUser()

    private var cartAdapter: CartAdapter? = null
    private var products: ArrayList<Product> = ArrayList()
    private var nonMultipleProductList: ArrayList<Product> = ArrayList()
    private lateinit var STORE_ID: String

    //STRIPE VARS
    private lateinit var paymentSession: PaymentSession
    private lateinit var selectedPaymentMethod: PaymentMethod
    private val stripe: Stripe by lazy {
        Stripe(
            applicationContext,
            PaymentConfiguration.getInstance(applicationContext).publishableKey
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        STORE_ID = intent.getStringExtra("STORE_ID")!!
        Toast.makeText(this, "Store id: " + STORE_ID, Toast.LENGTH_SHORT).show()

        //LOAD ITEMS THAT WHERE ADDED TO THE CART
        loadItemsInCart()

        payButton.isEnabled = false


        //SETUP PAYMENT SESSION AND SETTINGS
        setupPaymentSession()

        //CHOOSE PAYMENT METHOD
        tvSelectPaymentMethod.setOnClickListener {
            paymentSession.presentPaymentMethodSelection()
        }

        //PAYMENT BUTTON
        payButton.setOnClickListener {
            confirmPayment(selectedPaymentMethod.id!!)
        }
    }


    private fun loadItemsInCart() {
        firebaseRepo.getItemsInCart(currentUser!!.email!!, STORE_ID).addOnCompleteListener {
            if (it.isSuccessful) {
                for (document in it.result!!) {

                    val product = document.toObject(Product::class.java)
                    products.add(product)


                }





                setupCartRecyclerView(nonMultipleProductList)

            } else {
                Log.d("Error", "Error: ${it.exception!!.message}")
            }
        }
    }

    private fun setupCartRecyclerView(nonMultiProducts: ArrayList<Product>) = rvCart.apply {
        cartAdapter = CartAdapter(nonMultiProducts, this@PaymentActivity)
        adapter = cartAdapter

        val mLayoutManager = LinearLayoutManager(this@PaymentActivity)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager = mLayoutManager
    }


    private fun setupPaymentSession() {

        //TODO: SWITCH TEST KEY WITH REAL KEY
        //SETTING TEST API
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51Gr2G4A3lraCdm68rGEY53pzYkgTG02H3KdwRvCrymX4sh8gZtL3a9Os5d5TfMQ5hr0SIO0FOzt70FJlnuX3xOXw00H3BagLBd"
        )

        //Setup Customer Session
        CustomerSession.initCustomerSession(this, FirebaseEphemeralKeyProvider())

        //Setup a payment session
        paymentSession = PaymentSession(
            this, PaymentSessionConfig.Builder()
                .setShippingInfoRequired(false)
                .setShippingMethodsRequired(false)
                .setBillingAddressFields(BillingAddressFields.None)
                .setShouldShowGooglePay(true)
                .build()
        )

        paymentSession.init(
            object : PaymentSession.PaymentSessionListener {

                override fun onPaymentSessionDataChanged(data: PaymentSessionData) {
                    Log.d("STRIPE", "Paymentsession has changed")
                    Log.d(
                        "STRIPE",
                        "${data.isPaymentReadyToCharge} <> ${data.paymentMethod}"
                    )

                    if (data.isPaymentReadyToCharge) {
                        Log.d("STRIPE", "Ready to charge")
                        payButton.isEnabled = true


                        data.paymentMethod?.let {
                            Log.d(
                                "STRIPE",
                                "${it.card?.brand} card ends with ${it.card?.last4}"
                            )
                            tvSelectPaymentMethod.text =
                                "${it.card?.brand} card ends with ${it.card?.last4}"
                            selectedPaymentMethod = it
                        }

                    }
                }

                override fun onCommunicatingStateChanged(isCommunicating: Boolean) {
                    Log.d("STRIPE", "is Communicating $isCommunicating")
                }

                override fun onError(errorCode: Int, errorMessage: String) {
                    Log.e("STRIPE", "error: $errorCode  $errorMessage")
                }


            }
        )

    }

    private fun confirmPayment(paymentMethodId: String) {
        payButton.isEnabled = false

        //SET PAYMENT PROCESSING UI SCREEN
        clProcessingPayment.visibility = View.VISIBLE

        val paymentCollection = Firebase.firestore
            .collection("stripe_customers").document(currentUser?.uid ?: "")
            .collection("payments")

        // Add a new document with a generated ID
        paymentCollection.add(
            hashMapOf(
                "amount" to 8800,
                "currency" to "hkd"
            )
        )
            .addOnSuccessListener { documentReference ->
                Log.d("payment", "DocumentSnapshot added with ID: ${documentReference.id}")
                documentReference.addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.w("payment", "Listen failed.", e)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("payment", "Current data: ${snapshot.data}")
                        val clientSecret = snapshot.data?.get("client_secret")
                        Log.d("payment", "Create paymentIntent returns $clientSecret")
                        clientSecret?.let {
                            stripe.confirmPayment(
                                this, ConfirmPaymentIntentParams.createWithPaymentMethodId(
                                    paymentMethodId,
                                    (it as String)
                                )
                            )

                            tvAmount.text = getString(R.string.thank_you_for_your_payment)
                            tvProcessingPayment.visibility = View.GONE
                            progressPaymentBar.visibility = View.GONE
                            lottiePaymentComplete.visibility = View.VISIBLE
                            lottiePaymentComplete.loop(false)
                            lottiePaymentComplete.playAnimation()




                            Toast.makeText(
                                applicationContext,
                                "Payment Successful!!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Log.e("payment", "Current payment intent : null")
                        payButton.isEnabled = true
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w("payment", "Error adding document", e)
                payButton.isEnabled = true
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            paymentSession.handlePaymentData(requestCode, resultCode, data)
        }
    }
}