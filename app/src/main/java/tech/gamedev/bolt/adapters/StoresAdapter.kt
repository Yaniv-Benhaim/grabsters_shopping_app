package tech.gamedev.bolt.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_store.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Store

class StoresAdapter(private val stores: List<Store>, val context: Context): RecyclerView.Adapter<StoresAdapter.StoreViewHolder>() {

    inner class StoreViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_store,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = stores[position]

        //SET BACKGROUND IMAGE
        Glide.with(context)
            .load(store.getImgBackground())
            .into(holder.itemView.ivStoreImageBackground)

        //SET LOGO IMAGE
        Glide.with(context)
            .load(store.getImgLogo())
            .into(holder.itemView.ivStoreImageLogo)
    }




override fun getItemCount(): Int {
       return stores.size
    }

}




