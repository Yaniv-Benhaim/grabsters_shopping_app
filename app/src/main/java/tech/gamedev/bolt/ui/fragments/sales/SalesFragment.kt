package tech.gamedev.bolt.ui.fragments.sales

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_featured.*
import tech.gamedev.bolt.MainActivity
import tech.gamedev.bolt.R
import tech.gamedev.bolt.ui.adapters.CategoryAdapter
import tech.gamedev.bolt.ui.adapters.OnProductClickedListener
import tech.gamedev.bolt.ui.adapters.OnVisitClickedListener


class SalesFragment : Fragment(R.layout.fragment_featured), OnProductClickedListener,
    OnVisitClickedListener {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //SET VIEWPAGER WITH FRAGMENTS
        viewPagerFeatured.adapter = CategoryAdapter(childFragmentManager)
        tabLayoutFeatured.setupWithViewPager(viewPagerFeatured)

        //SET CURRENT LOCAL CITY TO UI
        tvFeaturedCity.text = MainActivity.currentCity

    }


}


