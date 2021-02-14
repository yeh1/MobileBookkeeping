package com.example.mobilebookkeeping.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilebookkeeping.R
import com.google.firebase.firestore.FirebaseFirestore

class CategoryFragment : Fragment(){

    lateinit var  recyclerView: RecyclerView
    val categoryRef = FirebaseFirestore
        .getInstance()
        .collection("category")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        recyclerView = inflater.inflate(R.layout.fragment_category, container, false) as RecyclerView
//        Log.d("tag","hello get view")
        val adapter = CategoryAdapter(context)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

//        adapterRecord = adapter


        return recyclerView
    }



}