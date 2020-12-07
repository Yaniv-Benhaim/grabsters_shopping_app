package tech.gamedev.bolt.ui.adapters

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
import tech.gamedev.bolt.ui.fragments.store.StoreFragmentDirections

class ProductFirestoreAdapterLandscape(options: FirestoreRecyclerOptions<Product>) :
    FirestoreRecyclerAdapter<Product, ProductFirestoreAdapterLandscape.ProductHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int, model: Product) {
        Glide.with(holder.itemView)
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
                        StoreFragmentDirections.actionStoreFragmentToProductDetailFragment(model.getProductId())
                    it.findNavController().navigate(action)
                }

            }

        }


    }

    inner class ProductHolder(view: View) : RecyclerView.ViewHolder(view)


}


