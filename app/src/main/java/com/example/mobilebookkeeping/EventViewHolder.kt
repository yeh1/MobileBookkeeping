package com.example.mobilebookkeeping

import android.view.View
import android.view.View.OnLongClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.detail_row_view.view.*


class EventViewHolder(itemView: View, adapter: EventAdapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val amountTextView = itemView.expenses_view_text_indetail as TextView
    private val commentTextView = itemView.comment_view_text_indetail as TextView



    init {
        itemView.setOnClickListener{
            adapter.notifyDataSetChanged()
        }

        itemView.setOnLongClickListener(OnLongClickListener {_ ->
            adapter.removeItem(this)
            true
        })
}


    fun bind(event: MyEvent){
        amountTextView.text = event.amount.toString()
        commentTextView.text = event.comment
    }

    override fun onClick(view: View?) {

    }

}