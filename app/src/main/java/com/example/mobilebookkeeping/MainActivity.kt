package com.example.mobilebookkeeping

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.mobilebookkeeping.category.Category
import com.example.mobilebookkeeping.category.CategoryFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val adapter = EventAdapter(ArrayList())
    private val dashboardFragment = DashboardFragment()
    private val profileFragment = ProfileFragment()
    private val addFragment = NewEventFragment(adapter, true)
    private val transactionFragment = addFragment.transactionFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        var switchTo : Fragment? = null
        adapter.transFragment = transactionFragment
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





//    override fun OnSelected(category: Category) {
////        setContentView(R.layout.fragment_category)
//        val ft = supportFragmentManager.beginTransaction()
//        ft.replace(R.id.fragment_container, CategoryFragment())
//        ft.commit()
//    }

}