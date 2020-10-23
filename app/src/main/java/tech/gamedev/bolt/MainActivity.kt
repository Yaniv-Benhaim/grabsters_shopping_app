package tech.gamedev.bolt

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        //SETTING UP TOP AND BOTTOM TOOLBAR
        /*setSupportActionBar(toolbar)*/
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        bottomNavigationView.setOnNavigationItemReselectedListener {/* NO-OP */ }

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.featuredFragment, R.id.deliveryFragment, R.id.nearbyFragment, R.id.searchFragment, R.id.profileFragment ->
                        bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }


    companion object{
        var personName: String? = null
        var personGivenName: String? = null
        var personId:String? = null
        var personPhoto: Uri? = null
        var personEmail: String? = null

    }

    }
