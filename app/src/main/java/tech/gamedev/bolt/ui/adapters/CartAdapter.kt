package tech.gamedev.bolt.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_cart.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product


class CartAdapter(val products: ArrayList<Product>, val context: Context) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val product = products[position]
        holder.itemView.apply {
            tvProductNameCart.text = product.getName()
            tvItemNumber.text = product.getProductId()
            tvTotalAmount.text = product.getPrice().toString()

            Glide.with(context)
                .load(product.getProductImg()).dontAnimate()
                .into(ivProductExample)
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }


}