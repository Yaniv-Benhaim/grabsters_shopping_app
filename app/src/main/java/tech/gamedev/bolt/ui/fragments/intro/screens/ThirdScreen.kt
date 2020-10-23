package tech.gamedev.bolt.ui.fragments.intro.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_first_screen.view.*
import kotlinx.android.synthetic.main.fragment_third_screen.*
import tech.gamedev.bolt.R


class ThirdScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.vpIntro)

        view.btnNext1.setOnClickListener {
            viewPager?.currentItem = 3
        }

        // Inflate the layout for this fragment
        return view
    }


}