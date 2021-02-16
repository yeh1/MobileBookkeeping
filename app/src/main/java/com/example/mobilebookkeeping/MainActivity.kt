package com.example.mobilebookkeeping

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobilebookkeeping.PieChartActivity
import com.example.mobilebookkeeping.R
import com.example.mobilebookkeeping.category.Category
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_add_category.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(), LoginFragment.OnLoginButtonPressedListener {

    private val dashboardFragment = DashboardFragment()
    lateinit var profileFragment : ProfileFragment
    lateinit var addFragment : NewEventFragment
    lateinit var transactionFragment : TransactionFragment
    lateinit var adapter : EventAdapter


    private val RC_SIGN_IN = 1
    val auth = FirebaseAuth.getInstance()
    lateinit var authListener: FirebaseAuth.AuthStateListener
    lateinit var swtichBar : View
    lateinit var addButton :FloatingActionButton


    val categoryRef = FirebaseFirestore
            .getInstance()
            .collection("category")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        initializeListeners()

    }


    fun  setListeners(){
        var switchTo : Fragment? = transactionFragment
        adapter.transFragment = transactionFragment
        transactionFragment.adapter = adapter


        nav_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    val allCategory = adapter.getAllCategory()
                    val amounts = ArrayList<Int>()
                    val I = Intent(this@MainActivity, PieChartActivity::class.java)
                    for(category in allCategory){
                        amounts.add(adapter.getAmountForCategory(category))
                    }
                    I.putStringArrayListExtra("allCategory", allCategory)
                    I.putIntegerArrayListExtra("amounts", amounts)
                    startActivity(I)


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

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
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
        swtichBar = findViewById<View>(R.id.nav_view)
        addButton = findViewById<FloatingActionButton>(R.id.fab)

        authListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser

//            Log.d("Tag", "In auth listener, User: $user")
            if (user != null) {
                Log.d("Tag", "UID: ${user.uid}")
//                Log.d("Tag", "Name: ${user.displayName}")
//                Log.d("Tag", "Email: ${user.email}")
//                Log.d("Tag", "Photo: ${user.photoUrl}")
//                Log.d("Tag", "Phone: ${user.phoneNumber}")
                profileFragment = ProfileFragment.newInstance(user.uid)
                adapter = EventAdapter(ArrayList(), user.uid)
                addFragment = NewEventFragment.newInstance(adapter, true, user.uid)
                transactionFragment = addFragment.transactionFragment

                val userRef =  FirebaseFirestore.getInstance()
                    .collection("user")
                    .document(user.uid)

                if (userRef == null){
                    var u =  HashMap<String, String>()
                    u.put("budget", "0");
                    u.put("username", "Username");
                    userRef.set(u).addOnSuccessListener {
//                            Log.d("tag", "put user successs")
                    }
                }




                setListeners()

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
            R.id.action_settings -> {
                showAddCategoryDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun addNewCategory(cate: Category){
        categoryRef.add(cate)
    }

    private fun showAddCategoryDialog(){
        val builder =AlertDialog.Builder(this)
        builder.setTitle(R.string.add_category)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_category, null, false)
        builder.setView(view)


        builder.setPositiveButton(android.R.string.ok){ _, _ ->
            val category = Category()
            category.name = view.category_name.text.toString()
            addNewCategory(category)
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.create().show()
    }

    override fun onLoginButtonPressed() {
        launchLoginUI()
    }

    private fun launchLoginUI() {
        // TODO: Build a login intent and startActivityForResult(intent, ...)
        // For details, see https://firebase.google.com/docs/auth/android/firebaseui#sign_in
        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
//                AuthUI.IdpConfig.PhoneBuilder().build(),
//                AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val loginIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.user)
                .build()

        startActivityForResult(loginIntent, RC_SIGN_IN)
    }

}