package com.example.mobilebookkeeping

import android.view.View
import android.view.View.OnLongClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mob.MyEvent
import kotlinx.android.synthetic.main.detail_row_view.view.*


class EventViewHolder(itemView: View, adapter: EventAdapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val titleTextView = itemView.event_name_view_text_indetail as TextView
    private val expenseTextView = itemView.expenses_view_text_indetail as TextView
    private val incomeTextView = itemView.income_view_text_indetail as TextView
    private val commentTextView = itemView.comment_view_text_indetail as TextView




    init {


        itemView.setOnClickListener{
            adapter.toggleEvent(adapterPosition)
            adapter.notifyDataSetChanged()

        }

        itemView.setOnLongClickListener(OnLongClickListener {_ ->
            adapter.removeItem(this)
            true
        })
}


    fun bind(event: MyEvent){

        titleTextView.text = event.title
        if(event.isExpense) {
            expenseTextView.text = "$" + event.amount.toString()
            incomeTextView.text = "$0"
        }else{
            expenseTextView.text = "$0"
            incomeTextView.text = "$" + event.amount.toString()
        }

        commentTextView.text = event.comment
    }

    override fun onClick(view: View?) {

    }

}