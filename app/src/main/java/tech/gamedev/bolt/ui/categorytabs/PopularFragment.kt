package tech.gamedev.bolt.ui.categorytabs

import android.graphics.Color
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
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_popular.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.ImageSlide
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.models.Store
import tech.gamedev.bolt.data.repositories.FirebaseRepo
import tech.gamedev.bolt.ui.adapters.*
import tech.gamedev.bolt.ui.fragments.sales.SalesFragmentDirections
import tech.gamedev.bolt.viewmodels.MainViewModel


class PopularFragment : Fragment(), OnVisitClickedListener {

    private var slides = ArrayList<ImageSlide>()
    private var storeAdapter: StoresAdapter? = null
    private var storesList: ArrayList<Store> = ArrayList()

    private var productList: ArrayList<Product> = ArrayList()
    private val firebaseRepo: FirebaseRepo = FirebaseRepo()
    private val mainViewModel: MainViewModel = MainViewModel()

    private lateinit var popularProductsAdapter: PopularProductsAdapter

    //FIRESTORE RECYCLER
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storeRef: CollectionReference = db.collection("sellers")
    private var productFirestoreAdapter: ProductFirestoreAdapter? = null
    private var storeWithProductsAdapter: StoreWithProductsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //MAIN IMAGE SLIDER
        setupImageSlider()

        //CHECK CURRENT USER
        checkUser()


    }

    private fun setupImageSlider() {
        if (slides.size < 2) {
            setupMainSlider()
        }

        imageSlider.setSliderAdapter(SliderAdapter2(requireContext(), slides))
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        imageSlider.indicatorSelectedColor = Color.WHITE
        imageSlider.indicatorUnselectedColor = Color.GRAY
        imageSlider.scrollTimeInSec = 5 //set scroll delay in seconds :
        imageSlider.startAutoCycle()
    }

    private fun setupMainSlider() {
        slides.add(
            ImageSlide(
                "https://firebasestorage.googleapis.com/v0/b/bolt-b3e8c.appspot.com/o/delivery_blurred_slide.jpg?alt=media&token=d3a7fce4-62ea-4247-8763-41ca19132721",
                "Looking for Work?",
                "Call us!"
            )
        )
        slides.add(
            ImageSlide(
                "https://firebasestorage.googleapis.com/v0/b/bolt-b3e8c.appspot.com/o/holding_hands_slide.jpg?alt=media&token=cbddd6f2-0b7f-40ab-8e6a-d13c4863837d",
                "Follow us on",
                "Instagram"
            )
        )
        slides.add(
            ImageSlide(
                "https://firebasestorage.googleapis.com/v0/b/bolt-b3e8c.appspot.com/o/pexels-artem-beliaikin-994517.jpg?alt=media&token=a21e3343-61fb-4b8b-bd09-e9c18d5df918",
                "Follow us on",
                "Facebook"
            )
        )


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
                    storesList.add(store)
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


    private fun setupRecyclerView() = rvNewStores.apply {
        //TODO: SWITCH ADAPTER TO FIRESTORE ADAPTER
        storeAdapter = StoresAdapter(storesList, requireContext(), this@PopularFragment)
        adapter = storeAdapter

        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager = mLayoutManager
    }


    private fun setupGamingProductsRecyclerView() = rvGaming.apply {

        val query: Query = storeRef
        val options = FirestoreRecyclerOptions.Builder<Store>()
            .setQuery(query, Store::class.java)
            .setLifecycleOwner(this@PopularFragment)
            .build()

        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager = mLayoutManager

        storeWithProductsAdapter = StoreWithProductsAdapter(options, requireContext())
        adapter = storeWithProductsAdapter


    }


    override fun onStoreClick(storeIdNumber: String, position: Int) {

        val action = SalesFragmentDirections.actionSalesFragmentToStoreFragment(storeIdNumber)
        findNavController().navigate(action)
    }

    override fun onStop() {
        super.onStop()
        productFirestoreAdapter?.stopListening()
        storeWithProductsAdapter?.onStop()
    }

}