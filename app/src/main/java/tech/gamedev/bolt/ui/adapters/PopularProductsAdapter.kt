package tech.gamedev.bolt.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product

class PopularProductsAdapter(
    private val products: List<Product>,
    val context: Context,
    var clickedListener: OnProductClickedListener
) : RecyclerView.Adapter<PopularProductsAdapter.StoreViewHolder>() {

    inner class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productBtn = itemView.btnInfo

        fun initialize(productIdNumber: String, action: OnProductClickedListener) {
            productBtn.setOnClickListener {

                itemView.lottieAddToCart.playAnimation()
                productBtn.animate().apply {
                    duration = 400
                    rotationYBy(360f)
                }.withEndAction {
                    action.onItemClick(productIdNumber, adapterPosition)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product_portrait,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val product = products[position]

        //SET PRODUCT IMAGE


        Glide.with(context)
            .load(product.getProductImg())
            .into(holder.itemView.ivItemProductImage)

        holder.itemView.apply {
            tvPrice.text = product.getPrice().toString()
            tvProductName.text = product.getName()

        }

        holder.initialize(product.getProductId(), clickedListener)


    }


    override fun getItemCount(): Int {
        return products.size
    }

}

interface OnProductClickedListener {
    fun onItemClick(productIdNumber: String, position: Int) {

    }
}




