package com.example.mobilebookkeeping

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.mobilebookkeeping.category.Category
import com.example.mobilebookkeeping.category.CategoryFragment
import com.firebase.ui.auth.AuthUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), LoginFragment.OnLoginButtonPressedListener  {

    private val adapter = EventAdapter(ArrayList())
    private val dashboardFragment = DashboardFragment()
    private val profileFragment = ProfileFragment()
    private val addFragment = NewEventFragment(adapter, true)
    private val transactionFragment = addFragment.transactionFragment


    private val RC_SIGN_IN = 1
    val auth = FirebaseAuth.getInstance()
    lateinit var authListener: FirebaseAuth.AuthStateListener
    lateinit var swtichBar : View
    lateinit var addButton :FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        var switchTo : Fragment? = null
        adapter.transFragment = transactionFragment
        transactionFragment.adapter = adapter

        initializeListeners()
        swtichBar = findViewById<View>(R.id.nav_view)
        addButton = findViewById<FloatingActionButton>(R.id.fab)



        nav_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    switchTo = dashboardFragment
                }
                R.id.navigation_profile -> {
                    switchTo = profileFragment
                }
                R.id.navigation_transaction -> {
                    adapter.notifyDataSetChanged()
                    switchTo = transactionFragment
                }
            }
            if(switchTo != null){
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.fragment_container, switchTo!!)
                ft.commit()
            }
            true
        })



        addButton.setOnClickListener { view ->
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, addFragment)
            ft.commit()
        }

    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authListener)
    }


    private fun initializeListeners() {
        // TODO: Create an AuthStateListener that passes the UID
        // to the MovieQuoteFragment if the user is logged in
        // and goes back to the Splash fragment otherwise.
        // See https://firebase.google.com/docs/auth/users#the_user_lifecycle
        authListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser

            Log.d("Tag", "In auth listener, User: $user")
            if (user != null) {
                Log.d("Tag", "UID: ${user.uid}")
//                Log.d("Tag", "Name: ${user.displayName}")
//                Log.d("Tag", "Email: ${user.email}")
//                Log.d("Tag", "Photo: ${user.photoUrl}")
//                Log.d("Tag", "Phone: ${user.phoneNumber}")

                switchToTransactionFragment(transactionFragment)
            } else {
                switchToLoginFragment()
            }
        }
    }

    fun switchToLoginFragment() {
        var addButton = findViewById<FloatingActionButton>(R.id.fab)
        swtichBar.setVisibility(View.INVISIBLE);
        addButton.setVisibility(View.INVISIBLE);
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, LoginFragment())
        ft.commit()
    }

    private fun switchToTransactionFragment(fragment: TransactionFragment) {
        swtichBar.setVisibility(View.VISIBLE)
        addButton.setVisibility(View.VISIBLE)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_container, fragment)
        ft.commit()
//        var picFragment  =  PicFragment()
//        val ft = supportFragmentManager.beginTransaction()
//        ft.replace(R.id.fragment_container, picFragment)
//        ft.commit()
    }





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.delete -> {
//
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLoginButtonPressed() {
        launchLoginUI()
    }

    private fun launchLoginUI() {
        // TODO: Build a login intent and startActivityForResult(intent, ...)
        // For details, see https://firebase.google.com/docs/auth/android/firebaseui#sign_in
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val loginIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_baseline_add_24)
                .build()

        startActivityForResult(loginIntent, RC_SIGN_IN)
    }

//    override fun OnSelected(category: Category) {
////        setContentView(R.layout.fragment_category)
//        val ft = supportFragmentManager.beginTransaction()
//        ft.replace(R.id.fragment_container, CategoryFragment())
//        ft.commit()
//    }

}