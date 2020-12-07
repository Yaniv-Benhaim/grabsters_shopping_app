package tech.gamedev.bolt.ui.fragments.store

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_store.*
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.repositories.FirebaseRepo
import tech.gamedev.bolt.ui.adapters.OnStoreProductClickedListener
import tech.gamedev.bolt.ui.adapters.ProductFirestoreAdapterLandscape
import tech.gamedev.bolt.viewmodels.StoreViewModel


class StoreFragment : Fragment(R.layout.fragment_store), OnStoreProductClickedListener {

    private lateinit var storeViewModel: StoreViewModel
    private lateinit var mLayoutManager: LinearLayoutManager
    private var firebaseRepo = FirebaseRepo()
    private var productList: ArrayList<Product> = ArrayList()
    private val args: StoreFragmentArgs by navArgs()
    private var storeId: String? = null


    private lateinit var categoryList: ArrayList<String>

    //FIRESTORE RECYCLER
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var storeRef: CollectionReference = db.collection("sellers")
    private var productFirestoreAdapter: ProductFirestoreAdapterLandscape? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        storeId = args.storeId
        storeRef = db.collection("sellers")
            .document(storeId.toString())
            .collection("products")


        //CHECK USER & Load Products from Store
        checkUser(storeId!!)


        //SET STORE CATEGORIES SPINNER

        categoryList = ArrayList<String>()
        categoryList.add("Electronics")
        categoryList.add("Gaming")
        categoryList.add("Audio")
        categoryList.add("Desktops")
        categoryList.add("Laptops")
        categoryList.add("Accessories")
        categoryList.add("Monitors")


        mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL


        val dataset: List<String> = categoryList
        nice_spinner.attachDataSource(dataset)



        nice_spinner.onSpinnerItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            OnSpinnerItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = parent?.getItemAtPosition(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

            override fun onItemSelected(
                parent: NiceSpinner?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = parent?.getItemAtPosition(position)
            }
        }
    }


    private fun checkUser(storeId: String) {
        if (firebaseRepo.getUser() == null) {
            firebaseRepo.createUser().addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    //User logged in load stores in rv
                    loadProducts(storeId)

                } else {
                    Log.d("Error", "Error: ${it.exception!!.message}")
                }
            }
        } else {
            //User logged in load stores in rv
            loadProducts(storeId)

        }

    }

    private fun loadProducts(storeId: String) {
        firebaseRepo.getStoreProductsList(storeId).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("STORE", "DOCUMENT DATA: ${it.result}")

                for (document in it.result!!) {
                    Log.d("STORE", "DOCUMENT DATA: ${document.data}")
                    val product = document.toObject(Product::class.java)
                    productList.add(product)
                }

                //SETUP RECYCLERVIEW
                setupRecyclerView()
                Log.d("STORE", "SUCCES: ${productList.size}")


            } else {
                Log.d("STORE", "Error: ${it.exception!!.message}")
            }
        }
    }

    private fun setupRecyclerView() = rvStoreProducts.apply {


        //SETUP QUERY
        val query: Query = storeRef
        val options = FirestoreRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .setLifecycleOwner(this@StoreFragment)
            .build()

        //SETUP LAYOUT MANAGER VERTICAL
        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager = mLayoutManager

        //ASSIGN ADAPTER WITH OPTIONS
        productFirestoreAdapter = ProductFirestoreAdapterLandscape(options)
        adapter = productFirestoreAdapter


    }

    override fun onProductClicked(productIdNumber: String) {
        val action =
            StoreFragmentDirections.actionStoreFragmentToProductDetailFragment(productIdNumber)
        findNavController().navigate(action)
    }

    override fun onStop() {
        super.onStop()
        productFirestoreAdapter?.stopListening()

    }
}







