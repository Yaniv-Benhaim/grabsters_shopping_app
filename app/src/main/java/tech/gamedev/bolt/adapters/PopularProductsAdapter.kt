package tech.gamedev.bolt.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_store.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.models.Store

class PopularProductsAdapter(private val products: List<Product>, val context: Context): RecyclerView.Adapter<PopularProductsAdapter.StoreViewHolder>() {

    inner class StoreViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
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
           btnBuyPopular.setOnClickListener {
               lottieAddToCart.playAnimation()
               btnBuyPopular.animate().apply {
                   duration = 400
                   rotationYBy(360f)
               }
           }
       }
    }




override fun getItemCount(): Int {
       return products.size
    }

}




