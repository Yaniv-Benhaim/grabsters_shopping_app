package tech.gamedev.bolt.ui.fragments.productdetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.stripe.android.PaymentSession
import com.stripe.android.model.PaymentMethod
import kotlinx.android.synthetic.main.product_detail_fragment.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.repositories.FirebaseRepo
import tech.gamedev.bolt.ui.payment.PaymentActivity
import tech.gamedev.bolt.viewmodels.LoginViewModel

class ProductDetailFragment : Fragment(R.layout.product_detail_fragment) {

    private val firebaseRepo = FirebaseRepo()
    private val args: ProductDetailFragmentArgs by navArgs()
    private var productIdNumber: String? = null
    lateinit var db: FirebaseFirestore
    private var product: Product? = null
    private var cartItemsAmount = 0
    private lateinit var user: FirebaseUser

    //STRIPE VARS
    private lateinit var paymentSession: PaymentSession
    private lateinit var selectedPaymentMethod: PaymentMethod


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //GET SAFE ARGUMENTS
        productIdNumber = args.itemNumber
        tvCartNotification.text = cartItemsAmount.toString()

        //LOAD PRODUCT
        loadProductFromFireStore()

        //LOAD PAYMENT SCREEN
        tvBtnAddToCart.setOnClickListener {
            //Add to Firestore cart within user profile
            addProductToCart()
            cartItemsAmount++
            tvCartNotification.text = cartItemsAmount.toString()
            Toast.makeText(
                requireContext(),
                "Added 1" + product?.getName() + "to your cart6",
                Toast.LENGTH_SHORT
            ).show()
        }

        fabCart.setOnClickListener {
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            intent.putExtra("STORE_ID", product?.getSeller())
            startActivity(intent)
        }


    }

    private fun addProductToCart() {

        user = firebaseRepo.getUser()!!
        db.collection("users")
            .document(user.email!!)
            .collection("cart")
            .document(product?.getSeller()!!)
            .collection("products")
            .document().set(product!!)
    }


    private fun loadProductFromFireStore() {
        //GET PRODUCT FROM DATABASE
        db = FirebaseFirestore.getInstance()

        val docRef = db.collection("all_products").document(productIdNumber!!)
        docRef.get().addOnSuccessListener { documentSnapshot ->


            Log.d("getProduct", documentSnapshot.data.toString())

            product = Product(
                documentSnapshot.data?.get("name").toString(),
                documentSnapshot.data?.get("description").toString(),
                documentSnapshot.data?.get("price") as Double,
                documentSnapshot.data?.get("stock") as Long,
                documentSnapshot.data?.get("productImg").toString(),
                documentSnapshot.data?.get("productId").toString(),
                documentSnapshot.data?.get("model").toString(),
                documentSnapshot.data?.get("specs").toString(),
                documentSnapshot.data?.get("seller").toString()

            )

            //SET PRODUCT IMAGE
            Glide.with(requireContext())
                .load(product!!.getProductImg())
                .into(ivProductDetailImage)

            //SET PRODUCT TITLE
            tvDetailProductTitle.text = product!!.getName()

            //SET PRODUCT DESCRIPTION
            tvDetailProductDescription.text = product!!.getDescription()

            //SET PRODUCT PRICE
            tvDetailProductPrice.text = product!!.getPrice().toString()

        }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "" + it, Toast.LENGTH_SHORT).show()
            }
    }

}





