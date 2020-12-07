package tech.gamedev.bolt.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item_store_with_products.view.*
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.models.Product
import tech.gamedev.bolt.data.models.Store

class StoreWithProductsAdapter(options: FirestoreRecyclerOptions<Store>, context: Context) :
    FirestoreRecyclerAdapter<Store, StoreWithProductsAdapter.StoreViewHolder>(options) {


    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var productRef: CollectionReference? = null
    private var productFirestoreAdapter: ProductFirestoreAdapter? = null


    inner class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        return StoreViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_store_with_products,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int, model: Store) {
        holder.itemView.apply {
            tvStoreNameWithProducts.text = model.getStoreName()


            productRef = db.collection("sellers")
                .document(model.getSellerUid())
                .collection("products")

            val query: Query = productRef!!
            val options = FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product::class.java)
                .build()

            val mLayoutManager = LinearLayoutManager(context)
            mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            rvWithinStoreItem.layoutManager = mLayoutManager
            productFirestoreAdapter = ProductFirestoreAdapter(options)
            rvWithinStoreItem.adapter = productFirestoreAdapter
            productFirestoreAdapter?.startListening()
        }
    }

    fun onStop() {
        productFirestoreAdapter?.stopListening()
    }


}