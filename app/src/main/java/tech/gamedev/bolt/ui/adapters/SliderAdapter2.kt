package tech.gamedev.bolt.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_slide.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.ImageSlide

class SliderAdapter2(val context: Context, private val slides: List<ImageSlide>) :
    SliderViewAdapter<SliderAdapter2.SliderAdapterVH>() {

    inner class SliderAdapterVH(itemview: View) : SliderViewAdapter.ViewHolder(itemview)

    override fun getCount(): Int {
        return slides.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        return SliderAdapterVH(
            LayoutInflater.from(parent?.context).inflate(R.layout.item_slide, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SliderAdapterVH?, position: Int) {
        val slide = slides[position]
        holder?.itemView?.apply {


            lottieDeliveryTruck.visibility = View.GONE
            tvSlideTitle.text = slide.getTitle()
            tvSlideDescription.text = slide.getDescription()
            Glide.with(holder.itemView)
                .load(slide.getImageUrl())
                .into(holder.itemView.ivSlideImage)


        }
    }


}