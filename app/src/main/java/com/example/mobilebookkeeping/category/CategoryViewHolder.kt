package com.example.mobilebookkeeping.category

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilebookkeeping.R

class CategoryViewHolder(itemView: View, adapter: CategoryAdapter): RecyclerView.ViewHolder(itemView){

    val name: TextView = itemView.findViewById(R.id.category_name)

    init {
        itemView.setOnClickListener{
            adapter.name = name.text.toString()
            adapter.backToNewEventFragment(adapterPosition)
            adapter.notifyDataSetChanged()
        }
    }

    fun bind(category: Category) {
        name.text = category.name
    }
}