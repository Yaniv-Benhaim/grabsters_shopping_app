package tech.gamedev.bolt.ui.categorytabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_computers.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.models.Store
import tech.gamedev.bolt.data.repositories.FirebaseRepo
import tech.gamedev.bolt.ui.adapters.ComputerStoresWithProductsAdapter
import tech.gamedev.bolt.ui.adapters.FashionStoresAdapter
import tech.gamedev.bolt.ui.adapters.OnVisitClickedListener
import tech.gamedev.bolt.ui.adapters.ProductFirestoreAdapter
import tech.gamedev.bolt.ui.fragments.sales.SalesFragmentDirections


class ComputersFragment : Fragment(), OnVisitClickedListener {


    private var storeAdapter: FashionStoresAdapter? = null
    private var storesList: ArrayList<Store> = ArrayList()

    private var productList: ArrayList<Product> = ArrayList()
    private val firebaseRepo: FirebaseRepo = FirebaseRepo()


    //FIRESTORE RECYCLER
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storeRef: CollectionReference = db.collection("sellers")
    private var productFirestoreAdapter: ProductFirestoreAdapter? = null
    private var computerStoresWithProductsAdapter: ComputerStoresWithProductsAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_computers, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //CHECK CURRENT USER
        checkUser()

    }

    private fun checkUser() {
        if (firebaseRepo.getUser() == null) {
            firebaseRepo.createUser().addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    //User logged in load stores in rv
                    loadStores()
                    loadMostBoughtProducts()
                    productFirestoreAdapter?.startListening()
                } else {
                    Log.d("Error", "Error: ${it.exception!!.message}")
                }
            }
        } else {
            //User logged in load stores in rv
            loadStores()
            loadMostBoughtProducts()
            productFirestoreAdapter?.startListening()

        }

    }


    private fun loadStores() {
        firebaseRepo.getStoresList().addOnCompleteListener {
            if (it.isSuccessful) {
                storesList = ArrayList<Store>()
                for (document in it.result!!) {

                    val store = document.toObject(Store::class.java)

                    if (store.getCategory() == "computers") {
                        storesList.add(store)
                    }

                }

                //SETUP RECYCLERVIEW
                setupRecyclerView()


            } else {
                Log.d("Error", "Error: ${it.exception!!.message}")
            }
        }
    }

    private fun loadMostBoughtProducts() {
        firebaseRepo.getMostBoughtProductsList().addOnCompleteListener {
            if (it.isSuccessful) {
                productList = ArrayList<Product>()
                for (document in it.result!!) {

                    val product = document.toObject(Product::class.java)
                    productList.add(product)
                }

                //SETUP RECYCLERVIEW

                setupGamingProductsRecyclerView()


            } else {
                Log.d("Error", "Error: ${it.exception!!.message}")
            }
        }
    }


    private fun setupRecyclerView() = rvComputerStores.apply {
        //TODO: SWITCH ADAPTER TO FIRESTORE ADAPTER
        storeAdapter = FashionStoresAdapter(storesList, requireContext(), this@ComputersFragment)
        adapter = storeAdapter

        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager = mLayoutManager
    }


    private fun setupGamingProductsRecyclerView() = rvComputerStoresWithProducts.apply {

        val query: Query = storeRef
        val options = FirestoreRecyclerOptions.Builder<Store>()
            .setQuery(query, Store::class.java)
            .setLifecycleOwner(this@ComputersFragment)
            .build()

        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager = mLayoutManager

        computerStoresWithProductsAdapter =
            ComputerStoresWithProductsAdapter(options, requireContext())
        adapter = computerStoresWithProductsAdapter


    }

    override fun onStoreClick(storeIdNumber: String, position: Int) {

        val action = SalesFragmentDirections.actionSalesFragmentToStoreFragment(storeIdNumber)
        findNavController().navigate(action)
    }

    override fun onStop() {
        super.onStop()
        productFirestoreAdapter?.stopListening()
        computerStoresWithProductsAdapter?.onStop()
    }


}