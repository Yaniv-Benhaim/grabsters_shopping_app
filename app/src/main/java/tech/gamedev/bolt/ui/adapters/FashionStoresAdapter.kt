package tech.gamedev.bolt.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_store.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Store

class FashionStoresAdapter(
    private val stores: ArrayList<Store>,
    val context: Context,
    var clickedListener: OnVisitClickedListener
) : RecyclerView.Adapter<FashionStoresAdapter.FashionStoreViewHolder>() {

    inner class FashionStoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var visitBtn = itemView.btnVisitStore

        fun initialize(storeIdNumber: String, action: OnVisitClickedListener) {
            visitBtn.setOnClickListener {


                visitBtn.animate().apply {
                    duration = 400
                    rotationYBy(360f)
                }.withEndAction {
                    action.onStoreClick(storeIdNumber, adapterPosition)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FashionStoreViewHolder {
        return FashionStoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_store,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FashionStoreViewHolder, position: Int) {


        val store = stores[position]

        if (store.getCategory() == "fashion") {
            //SET BACKGROUND IMAGE
            Glide.with(context)
                .load(store.getImgBackground()).dontAnimate()
                .into(holder.itemView.ivStoreImageBackground)

            //SET LOGO IMAGE
            Glide.with(context)
                .load(store.getImgLogo())
                .into(holder.itemView.ivStoreImageLogo)

            holder.initialize(store.getSellerUid(), clickedListener)
        } else {
            holder.itemView.visibility = View.GONE
        }

    }


    override fun getItemCount(): Int {
        return stores.size
    }

}

interface OnFashionStoreVisitClickedListener {
    fun onStoreClick(storeIdNumber: String, position: Int) {

    }
}




