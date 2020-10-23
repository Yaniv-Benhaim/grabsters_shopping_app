package tech.gamedev.bolt.ui.fragments.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_splash.*
import tech.gamedev.bolt.R


class SplashFragment : Fragment(R.layout.fragment_splash) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        Handler().postDelayed({

            if(false){
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }else {
                findNavController().navigate(R.id.action_splashFragment_to_introViewPagerFragment)
            }
            },4000)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            tvAppName.animate().apply {
                duration = 2000
                scaleXBy(0.4f)
                scaleYBy(0.4f)

            }.start()


    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }
}