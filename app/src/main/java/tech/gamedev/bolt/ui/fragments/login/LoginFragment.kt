package tech.gamedev.bolt.ui.fragments.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_login.*
import tech.gamedev.bolt.MainActivity
import tech.gamedev.bolt.R
import tech.gamedev.bolt.adapters.ViewPagerAdapter
import tech.gamedev.bolt.data.Constants.AUTH_REQUEST_CODE
import tech.gamedev.bolt.ui.fragments.intro.screens.*


class LoginFragment : Fragment(R.layout.fragment_login) {

    private var user: FirebaseUser? = null
    private var userMutableLiveData: MutableLiveData<FirebaseUser?>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return super.onCreateView(inflater, container, savedInstanceState)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //LOGIN SETUP
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI(account)
            Toast.makeText(context, "Welcome ${account?.displayName}", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
            Toast.makeText(context, "Login Failed please try again", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI(acct: GoogleSignInAccount?) {
        if (acct != null) {
            MainActivity.personName = acct.displayName
            MainActivity.personGivenName = acct.givenName
            MainActivity.personEmail = acct.email
            MainActivity.personId = acct.id
            MainActivity.personPhoto = acct.photoUrl
            Toast.makeText(requireContext(), MainActivity.personEmail, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_global_featuredFragment)
        } else {
            Toast.makeText(
                requireContext(),
                "Login was not successful, please try again",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}