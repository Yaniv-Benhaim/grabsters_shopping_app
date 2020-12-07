package tech.gamedev.bolt.ui.fragments.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*
import tech.gamedev.bolt.MainActivity
import tech.gamedev.bolt.R
import tech.gamedev.bolt.data.Constants.AUTH_REQUEST_CODE
import tech.gamedev.bolt.viewmodels.LoginViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth
        return super.onCreateView(inflater, container, savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //LOGIN SETUP
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        btnGoogleSignIn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, AUTH_REQUEST_CODE)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTH_REQUEST_CODE) {
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    // ...
                    Snackbar.make(requireView(), "Authentication Failed.", Snackbar.LENGTH_SHORT)
                        .show()
                    updateUI(null)
                }

                // ...
            }
    }


    private fun updateUI(acct: FirebaseUser?) {
        if (acct != null) {

            //CHECK IF USER EXISTS AND IF NOT ADD TO USER DATABASE
            checkIfUserExists(acct)

            MainActivity.personName = acct.displayName
            MainActivity.personEmail = acct.email
            MainActivity.personPhoto = acct.photoUrl
            /*Toast.makeText(requireContext(), MainActivity.personEmail, Toast.LENGTH_SHORT).show()*/
            findNavController().navigate(R.id.action_global_salesFragment)
        } else {
            Log.d("LOGIN", "NOT LOGGED IN YET")
        }

    }

    private fun addNewUserToFirestore(acct: FirebaseUser?) {

        val db = FirebaseFirestore.getInstance()

        val user = hashMapOf(
            "name" to "${acct?.displayName}",
            "email" to "${acct?.email}",
            "img" to "${acct?.photoUrl}"

        )

        db.collection("users").document("${acct?.email}")
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }


    }

    private fun checkIfUserExists(acct: FirebaseUser?) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document("${acct?.email}").get()
            .addOnCompleteListener(OnCompleteListener {
                if (it.result?.exists()!!) {
                } else {
                    addNewUserToFirestore(acct)
                }
            })


    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

}