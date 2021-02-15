package com.example.mob

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mobilebookkeeping.PieChartActivity
import com.example.mobilebookkeeping.R
import com.example.mobilebookkeeping.category.Category
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private val adapter = EventAdapter(ArrayList())
    private val dashboardFragment = DashboardFragment()
    private val profileFragment = ProfileFragment()
    private val addFragment = NewEventFragment(adapter, true)
    private val transactionFragment = addFragment.transactionFragment

    val categoryRef = FirebaseFirestore
            .getInstance()
            .collection("category")

    init {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        var switchTo : Fragment? = transactionFragment
        adapter.transFragment = transactionFragment
        transactionFragment.adapter = adapter
        val ft = supportFragmentManager.beginTransaction()
        Log.d("myTag", adapter.events.size.toString())
        Log.d("myTag", transactionFragment.adapter!!.events.size.toString())

        ft.replace(R.id.fragment_container, switchTo!!)
        ft.commit()



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

}