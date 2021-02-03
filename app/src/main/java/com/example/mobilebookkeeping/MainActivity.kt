package com.example.mobilebookkeeping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.mobilebookkeeping.ui.NewEventFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val dashboardFragment = DashboardFragment()
    private var profileFragment = ProfileFragment()
    private var transactionFragment = TransactionFragment()
    private val addFragment = NewEventFragment()


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))
//        var switchTo : Fragment? = null
//        nav_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
//
//            when (item.itemId) {
//                R.id.navigation_dashboard -> {
//                    switchTo = dashboardFragment
//                }
//                R.id.navigation_profile -> {
//                    switchTo = profileFragment
//                }
//                R.id.navigation_transaction -> {
//                    switchTo = transactionFragment
//                }
//            }
//            if(switchTo != null){
//                val ft = supportFragmentManager.beginTransaction()
//                ft.replace(R.id.fragment_container, switchTo!!)
//                ft.commit()
//            }
//            true
//        })
//
//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            switchTo = addFragment
//        }
//    }

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