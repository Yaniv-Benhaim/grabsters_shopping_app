package tech.gamedev.bolt.viewmodels


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import tech.gamedev.bolt.data.repositories.FirebaseRepo

class LoginViewModel : ViewModel() {

    private var firebaseRepo = FirebaseRepo()

    private val _user = MutableLiveData<FirebaseUser>()


    fun returnUser(): LiveData<FirebaseUser> {
        _user.value = firebaseRepo.getUser()
        return _user
    }


    fun addNewUserToFirestore(acct: FirebaseUser?) {

        val db = FirebaseFirestore.getInstance()

        val user = hashMapOf(
            "name" to "${acct?.displayName}",
            "email" to "${acct?.email}",
            "img" to "${acct?.photoUrl}"

        )

        db.collection("users").document("${acct?.email}")
            .set(user)
            .addOnSuccessListener {
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }


    }

    fun checkIfUserExists(acct: FirebaseUser?) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document("${acct?.email}").get()
            .addOnCompleteListener(OnCompleteListener {
                if (it.result?.exists()!!) {
                } else {
                    addNewUserToFirestore(acct)
                }
            })


    }


}