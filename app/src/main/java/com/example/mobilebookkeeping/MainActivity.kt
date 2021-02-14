package com.example.mobilebookkeeping

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.mobilebookkeeping.DashboardFragment
import com.example.mobilebookkeeping.EventAdapter
import com.example.mobilebookkeeping.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val adapter = EventAdapter(ArrayList())
    private val dashboardFragment = DashboardFragment()
    private val profileFragment = ProfileFragment()
    private val addFragment = NewEventFragment(adapter)
    private val transactionFragment = addFragment.transactionFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        var switchTo : Fragment? = null
        transactionFragment.adapter = adapter


        nav_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    switchTo = dashboardFragment
                }
                R.id.navigation_profile -> {
                    switchTo = profileFragment
                }
                R.id.navigation_transaction -> {
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

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, addFragment)
            ft.commit()
        }
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

}