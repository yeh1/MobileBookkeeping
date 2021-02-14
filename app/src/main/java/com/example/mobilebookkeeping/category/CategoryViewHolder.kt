package com.example.mobilebookkeeping.category

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilebookkeeping.R

class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val name: TextView = itemView.findViewById(R.id.category_name)


    fun bind(category: Category) {
        name.text = category.name
    }
}