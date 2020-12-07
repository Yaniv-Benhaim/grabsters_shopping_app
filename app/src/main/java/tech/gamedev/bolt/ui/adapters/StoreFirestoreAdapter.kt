package tech.gamedev.bolt.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.item_product.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.ui.fragments.sales.SalesFragmentDirections

class StoreFirestoreAdapter(options: FirestoreRecyclerOptions<Product>, context: Context) :
    FirestoreRecyclerAdapter<Product, StoreFirestoreAdapter.StoreViewHolder>(
        options
    ) {

    inner class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int, model: Product) {

        Glide.with(holder.itemView.context)
            .load(model.getProductImg())
            .into(holder.itemView.ivItemProductImage)

        holder.itemView.apply {
            tvPrice.text = model.getPrice().toString()
            tvProductName.text = model.getName()

            btnInfo.setOnClickListener {

                btnInfo.animate().apply {
                    duration = 400
                    rotationYBy(360f)

                }.withEndAction {
                    val action =
                        SalesFragmentDirections.actionSalesFragmentToProductDetailFragment(model.getProductId())
                    it.findNavController().navigate(action)
                }

            }
        }
    }
}