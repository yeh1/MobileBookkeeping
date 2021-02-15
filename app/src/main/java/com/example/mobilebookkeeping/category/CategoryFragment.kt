package com.example.mobilebookkeeping.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mob.EventAdapter
import com.example.mob.MyEvent
import com.example.mobilebookkeeping.R
import com.google.firebase.firestore.FirebaseFirestore

class CategoryFragment(var eventAdapter: EventAdapter) : Fragment(){

    lateinit var  recyclerView: RecyclerView
    var name : String? = null
    val categoryRef = FirebaseFirestore
        .getInstance()
        .collection("category")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = CategoryAdapter(context, eventAdapter)
        adapter.myActivity = this.requireActivity()
        recyclerView = inflater.inflate(R.layout.fragment_category, container, false) as RecyclerView
        recyclerView.adapter = adapter
        this.name = adapter.name
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

//        adapterRecord = adapter


        return recyclerView
    }

//    fun setNameTo(name : String){
//        this.name = name
//    }


}