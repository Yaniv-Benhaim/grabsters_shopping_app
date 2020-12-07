package tech.gamedev.bolt.ui.fragments.rentals

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_rentals.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.ImageSlide
import tech.gamedev.bolt.ui.adapters.SliderAdapter2


class RentalsFragment : Fragment(R.layout.fragment_rentals) {

    private var slides = ArrayList<ImageSlide>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SET IMAGE SLIDER
        setupImageSlider()


    }


    private fun setupImageSlider() {
        setupMainSlider()
        imageSliderRentals.setSliderAdapter(SliderAdapter2(requireContext(), slides))
        imageSliderRentals.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSliderRentals.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        imageSliderRentals.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        imageSliderRentals.indicatorSelectedColor = Color.WHITE
        imageSliderRentals.indicatorUnselectedColor = Color.GRAY
        imageSliderRentals.scrollTimeInSec = 5 //set scroll delay in seconds :
        imageSliderRentals.startAutoCycle()
    }

    private fun setupMainSlider() {
        slides.add(
            ImageSlide(
                "https://firebasestorage.googleapis.com/v0/b/bolt-b3e8c.appspot.com/o/rentals.png?alt=media&token=7cf5e3f6-a982-4317-8b9c-40cc38237382",
                "",
                ""
            )
        )
        slides.add(
            ImageSlide(
                "https://firebasestorage.googleapis.com/v0/b/bolt-b3e8c.appspot.com/o/rentals.png?alt=media&token=7cf5e3f6-a982-4317-8b9c-40cc38237382",
                "",
                ""
            )
        )
        slides.add(
            ImageSlide(
                "https://firebasestorage.googleapis.com/v0/b/bolt-b3e8c.appspot.com/o/delivery.png?alt=media&token=37454662-d75a-4cea-a004-969e505c356f",
                "Fast Delivery",
                "Order now, get it within 60 minutes"
            )
        )
        slides.add(
            ImageSlide(
                "https://firebasestorage.googleapis.com/v0/b/bolt-b3e8c.appspot.com/o/fast_delivery.png?alt=media&token=7eeffb47-d26a-4dd1-bc61-9453519efba0",
                "Fast Delivery",
                "Order now, get it within 60 minutes"
            )
        )


    }

}