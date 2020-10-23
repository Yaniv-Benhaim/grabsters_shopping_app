package tech.gamedev.bolt.ui.fragments.featured

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_featured.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.adapters.PopularProductsAdapter
import tech.gamedev.bolt.adapters.StoresAdapter
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.models.Store


class FeaturedFragment : Fragment(R.layout.fragment_featured), View.OnClickListener {

    private lateinit var storeAdapter: StoresAdapter
    private var storesList: ArrayList<Store>? = null

    private lateinit var popularProductsAdapter: PopularProductsAdapter
    private var productList: ArrayList<Product>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    //CATEGORY BUTTONS




        //SETUP TEMPORARY ARRAY OF STORES
        setupArrayOfStores()

        //SETUP TEMPORARY ARRAY OF PRODUCTS
        setupArrayOfProducts()

        //SETUP RECYCLERVIEW
        setupRecyclerView()

        //SETUP RECYCLERVIEW
        setupPopularProductsRecyclerView()
    }

    private fun setupArrayOfStores() {

        storesList = ArrayList<Store>()
        storesList!!.add(
            Store(
                "Ivory",
                "Best place to buy all tech related gadgets",
                "https://www.ivory.co.il/images/tabManager/IVORY_IMAGE_DT.jpg",
                "https://eilat.city/images/5281-7806-%D7%90%D7%99%D7%99%D7%91%D7%95%D7%A8%D7%99-%D7%9E%D7%97%D7%A9%D7%91%D7%99%D7%9D-(%D7%A4%D7%A0%D7%99%D7%A0%D7%AA-%D7%90%D7%99%D7%9C%D7%AA)-%D7%90%D7%99%D7%9C%D7%AA-md.jpg"
            )
        )

        storesList!!.add(
            Store(
                "KSP",
                "Best place to buy all tech related gadgets",
                "https://ksp.co.il/newmap/photo/re1.png",
                "https://ksp.co.il/img/bigLogoKsp.png"

            )
        )

        storesList!!.add(
            Store(
                "IDigital",
                "Best place to buy all tech related gadgets",
                "https://www.idigital.co.il/files/iDigitalStore/Kfarsaba.jpg",
                "https://theverifier.co.il/wp-content/uploads/2019/06/idigital-logo.png"
            )
        )

        storesList!!.add(
            Store(
                "Ivory",
                "Best place to buy all tech related gadgets",
                "https://www.ivory.co.il/images/tabManager/IVORY_IMAGE_DT.jpg",
                "https://eilat.city/images/5281-7806-%D7%90%D7%99%D7%99%D7%91%D7%95%D7%A8%D7%99-%D7%9E%D7%97%D7%A9%D7%91%D7%99%D7%9D-(%D7%A4%D7%A0%D7%99%D7%A0%D7%AA-%D7%90%D7%99%D7%9C%D7%AA)-%D7%90%D7%99%D7%9C%D7%AA-md.jpg"
            )
        )

        storesList!!.add(
            Store(
                "Ivory",
                "Best place to buy all tech related gadgets",
                "https://www.ivory.co.il/images/tabManager/IVORY_IMAGE_DT.jpg",
                "https://eilat.city/images/5281-7806-%D7%90%D7%99%D7%99%D7%91%D7%95%D7%A8%D7%99-%D7%9E%D7%97%D7%A9%D7%91%D7%99%D7%9D-(%D7%A4%D7%A0%D7%99%D7%A0%D7%AA-%D7%90%D7%99%D7%9C%D7%AA)-%D7%90%D7%99%D7%9C%D7%AA-md.jpg"
            )
        )

        storesList!!.add(
            Store(
                "Ivory",
                "Best place to buy all tech related gadgets",
                "https://www.ivory.co.il/images/tabManager/IVORY_IMAGE_DT.jpg",
                "https://eilat.city/images/5281-7806-%D7%90%D7%99%D7%99%D7%91%D7%95%D7%A8%D7%99-%D7%9E%D7%97%D7%A9%D7%91%D7%99%D7%9D-(%D7%A4%D7%A0%D7%99%D7%A0%D7%AA-%D7%90%D7%99%D7%9C%D7%AA)-%D7%90%D7%99%D7%9C%D7%AA-md.jpg"
            )
        )

        storesList!!.add(
            Store(
                "Ivory",
                "Best place to buy all tech related gadgets",
                "https://www.ivory.co.il/images/tabManager/IVORY_IMAGE_DT.jpg",
                "https://eilat.city/images/5281-7806-%D7%90%D7%99%D7%99%D7%91%D7%95%D7%A8%D7%99-%D7%9E%D7%97%D7%A9%D7%91%D7%99%D7%9D-(%D7%A4%D7%A0%D7%99%D7%A0%D7%AA-%D7%90%D7%99%D7%9C%D7%AA)-%D7%90%D7%99%D7%9C%D7%AA-md.jpg"
            )
        )


    }

    private fun setupRecyclerView() = rvNewStores.apply {

            storeAdapter = storesList?.let { StoresAdapter(it,requireContext()) }!!
            adapter = storeAdapter

            val mLayoutManager = LinearLayoutManager(requireContext())
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = mLayoutManager
        }

    private fun setupPopularProductsRecyclerView() = rvPopular.apply {

        popularProductsAdapter = productList?.let{
            PopularProductsAdapter(it,requireContext())
        }!!
        adapter = popularProductsAdapter

        val mLayoutManager = LinearLayoutManager(requireContext())
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        layoutManager = mLayoutManager
    }

    private fun setupArrayOfProducts(){


        productList = ArrayList<Product>()
        productList!!.add(
            Product(
                "Asus Zenbook dual 2020",
                "Best place to buy all tech related gadgets",
                1999,
                2000,
                "https://theaxo.com/wp-content/uploads/2019/05/asus-zenbook-pro-duo-1-1280x720.jpg"
            )
        )

        productList!!.add(
            Product(
                "Samsung Galaxy note 20 Ultra",
                "Best place to buy all tech related gadgets",
                1199,
                2000,
                "https://www.slashgear.com/wp-content/uploads/2020/08/Galaxy-Note20-Ultra_Flat-on-Table-1-1280x720.jpg"
            )
        )

        productList!!.add(
            Product(
                "Apple - MacBook Pro 16 Inch",
                "Best place to buy all tech related gadgets",
                2499,
                2000,
                "https://cdn.vox-cdn.com/thumbor/mi4Hda8NW4j3jAxE5BXuydMxocs=/0x0:2732x1661/1200x800/filters:focal(1137x693:1573x1129)/cdn.vox-cdn.com/uploads/chorus_image/image/65691096/4464FEBF_F9A9_40BC_987C_267AD1D63F19.0.jpeg"
            )
        )

        productList!!.add(
            Product(
                "Sony Playstation 5 2020",
                "Best place to buy all tech related gadgets",
                999,
                2000,
                "https://cdn.mos.cms.futurecdn.net/7Qv4q73m9Nif9CpxtfVWs6.jpg"
            )
        )

        productList!!.add(
            Product(
                "DJI Mavic 2 Pro",
                "Best place to buy all tech related gadgets",
                899,
                2000,
                "https://www.sea-help.eu/wp-content/uploads/2019-12-05_news_seahelp_dji-mavic-2-pro-hasselblad.jpg"
            )
        )
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.llBtnCategoryLaptops -> animateImage(ivCategoryComputers)
            R.id.llBtnCategoryPhones -> animateImage(ivCategoryPhones)
            R.id.llBtnCategoryGaming -> animateImage(ivCategoryGaming)
            R.id.llBtnCategoryTelevision -> animateImage(ivCategoryTV)
            R.id.llBtnCategoryCameras -> animateImage(ivCategoryCamera)
        }
    }

    private fun animateImage(v:View){
        v.animate().apply {
            duration = 400
            rotationYBy(360f)
        }
    }


}