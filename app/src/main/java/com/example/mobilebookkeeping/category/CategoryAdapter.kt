package com.example.mobilebookkeeping.category

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilebookkeeping.MyEvent
import com.google.firebase.firestore.*

class CategoryAdapter (var context: Context?) : RecyclerView.Adapter<CategoryViewHolder>(){
    var categories =  ArrayList<Category>()
    val categoryRef = FirebaseFirestore
        .getInstance()
        .collection("category")


    init {
        categoryRef.addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
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
            .inflate(com.example.mobilebookkeeping.R.layout.category_row_view, parent, false)

        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories.get(position))

    }



}