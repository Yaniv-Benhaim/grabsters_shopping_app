package tech.gamedev.bolt.ui.fragments.intro.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_fifth_screen.view.*
import tech.gamedev.bolt.R


class FifthScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_fifth_screen, container, false)
        view.btnNext1.setOnClickListener {
            findNavController().navigate(R.id.action_introViewPagerFragment_to_loginFragment)
            onBoardingFinished()
        }
        return view
    }


    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}