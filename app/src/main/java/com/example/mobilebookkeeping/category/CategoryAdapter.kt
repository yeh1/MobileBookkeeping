package com.example.mobilebookkeeping.category

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.mob.*
import com.example.mobilebookkeeping.R
import com.google.firebase.firestore.*

class CategoryAdapter (var context: Context?, var eventAdapter: EventAdapter) : RecyclerView.Adapter<CategoryViewHolder>(){
    var categories =  ArrayList<Category>()
    var editPosition = eventAdapter.eventAdapterEditPosition
    var isNew: Boolean = eventAdapter.isNew
    var transFragment = CategoryFragment(eventAdapter)
    lateinit var myActivity: FragmentActivity
    var name: String = "!!"
    val categoryRef = FirebaseFirestore
        .getInstance()
        .collection("category")


    init {
        categoryRef.orderBy("name").addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            for (docChange in snapshot!!.documentChanges) {
                val category = Category.fromSnapshot(docChange.document)
                when (docChange.type) {
                    DocumentChange.Type.ADDED -> {
                        categories.add(0, category)
                        notifyItemInserted(0)
                    }
                    DocumentChange.Type.REMOVED -> {
                        val pos = categories.indexOfFirst { category.id == it.id }
                        categories.removeAt(pos)
                        notifyItemRemoved(pos)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val pos = categories.indexOfFirst { category.id == it.id }
                        categories[pos] = category
                        notifyItemChanged(pos)
                    }
                }
            }
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.category_row_view, parent, false)

        return CategoryViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return categories.size
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])

    }

    fun backToNewEventFragment(position: Int){
        if(isNew){
            eventAdapter.name = name
            eventAdapter.eventAdapterEditPosition = editPosition
            eventAdapter.category = categories[position].name
            val nextFrag = NewEventFragment(eventAdapter, true)
            nextFrag.editPosition = editPosition

            val ft : FragmentTransaction = (myActivity as FragmentActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container,nextFrag)
            ft.commit()
        }else {
            eventAdapter.name = name
            eventAdapter.category = categories[position].name
            val nextFrag = NewEventFragment(eventAdapter, false)
            nextFrag.editPosition = editPosition
            val ft: FragmentTransaction = (myActivity as FragmentActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container, nextFrag)
            ft.commit()
        }
    }



}