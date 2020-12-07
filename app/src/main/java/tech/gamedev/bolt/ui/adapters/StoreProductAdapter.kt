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

class StoreProductAdapter(
    var products: ArrayList<Product>,
    var productClickedListener: OnStoreProductClickedListener,
    var context: Context
) :
    RecyclerView.Adapter<StoreProductAdapter.ViewHolder>() {

    var liked = false

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image = itemView.ivItemProductImage
        /*var like = itemView.ivLike*/

        fun initialize(productIdNumber: String, action: OnStoreProductClickedListener) {
            image.setOnClickListener {
                action.onProductClicked(productIdNumber)
            }

            /*like.setOnClickListener {
                if (!liked){
                    liked = true
                    like.setImageResource(R.drawable.heart2)
                }else{
                    liked = false
                    like.setImageResource(R.drawable.heart_hollow)
                }
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = products[position]
        //SET BACKGROUND IMAGE
        Glide.with(context)
            .load(product.getProductImg())
            .into(holder.itemView.ivItemProductImage)

        holder.itemView.apply {
            tvPrice.text = product.getPrice().toString()
            tvProductName.text = product.getName()
        }

        holder.initialize(product.getProductId(), productClickedListener)
    }

    override fun getItemCount(): Int {
        return products.size
    }
}

interface OnStoreProductClickedListener {
    fun onProductClicked(productIdNumber: String)
}