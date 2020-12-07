package tech.gamedev.bolt.data.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class FirebaseRepo {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun createUser(): Task<AuthResult> {
        return firebaseAuth.signInAnonymously()
    }


    fun getStoresList(): Task<QuerySnapshot> {
        return firebaseFireStore.collection("sellers")
            .orderBy("name", Query.Direction.DESCENDING)
            .get()
    }

    fun getMostBoughtProductsList(): Task<QuerySnapshot> {
        return firebaseFireStore.collection("all_products")
            .get()
    }

    fun getStoreProductsList(sellerId: String): Task<QuerySnapshot> {
        return firebaseFireStore.collection("sellers")
            .document(sellerId)
            .collection("products")
            .get()
    }

    fun getAllProductsList(productIdNumber: String): Task<DocumentSnapshot> {
        return firebaseFireStore.collection("all_products").document(productIdNumber)
            .get()
    }

    fun getItemsInCart(userEmail: String, storeID: String): Task<QuerySnapshot> {
        return firebaseFireStore.collection("users")
            .document(userEmail)
            .collection("cart")
            .document(storeID)
            .collection("products")
            .get()
    }


}