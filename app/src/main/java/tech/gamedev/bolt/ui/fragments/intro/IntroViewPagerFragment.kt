package tech.gamedev.bolt.ui.fragments.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_intro_view_pager.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.ui.adapters.ViewPagerAdapter
import tech.gamedev.bolt.ui.fragments.intro.screens.*


class IntroViewPagerFragment : Fragment(R.layout.fragment_intro_view_pager) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro_view_pager,container,false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen(),
            FourthScreen(),
            FifthScreen()

        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.vpIntro.adapter = adapter

        return view
    }

}