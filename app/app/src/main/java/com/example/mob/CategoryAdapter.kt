package com.example.mob

import android.app.PendingIntent.getActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class CategoryAdapter() : RecyclerView.Adapter<EventViewHolder>() {



    val categoryRef = FirebaseFirestore
        .getInstance()
        .collection("categories")

    val categories: ArrayList<String> = categoryRef.

        init {
        categoryRef.addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            for (docChange in snapshot!!.documentChanges) {
                val event = MyEvent.fromSnapshot(docChange.document)
                when (docChange.type) {
                    DocumentChange.Type.ADDED -> {
                        events.add(0, event)
                        notifyItemInserted(0)
                    }
                    DocumentChange.Type.REMOVED -> {
                        val pos = events.indexOfFirst { event.id == it.id }
                        events.removeAt(pos)
                        notifyItemRemoved(pos)
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val pos = events.indexOfFirst { event.id == it.id }
                        events[pos] = event
                        notifyItemChanged(pos)
                    }
                }
            }
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, index: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_row_view, parent, false)
        return CategoryViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(viewHolder: EventViewHolder, index: Int) {
        viewHolder.bind(events[index])
    }

}