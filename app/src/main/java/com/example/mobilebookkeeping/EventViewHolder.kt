package com.example.mob

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilebookkeeping.R
import kotlinx.android.synthetic.main.detail_row_view.view.*


class EventViewHolder(itemView: View, adapter: EventAdapter) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val detailRowView = itemView.detail_row_view
    private val titleTextView = itemView.event_name_view_text_indetail as TextView
    private val expenseTextView = itemView.expenses_view_text_indetail as TextView
    private val incomeTextView = itemView.income_view_text_indetail as TextView
    private val commentTextView = itemView.comment_view_text_indetail as TextView
    private val icon = itemView.image



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

        if(event.isDateEvent) {
            detailRowView.setCardBackgroundColor(Color.parseColor("#FFC107"))
            icon.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
            titleTextView.text = event.title
        }
        else {
            detailRowView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            icon.setImageResource(R.drawable.shopping)
            titleTextView.text = event.category
        }
        if(event.isExpense) {
            expenseTextView.text = "$" + event.amount.toString()
            incomeTextView.text = ""
        }else{
            expenseTextView.text = ""
            incomeTextView.text = "$" + event.amount.toString()
        }

        if(event.isDateEvent){
            expenseTextView.text = "$" + event.amount.toString()
            incomeTextView.text = "$" + event.income.toString()
        }
        when (event.category) {
            "Food" -> icon.setImageResource(R.drawable.meal)
            "Activity" -> icon.setImageResource(R.drawable.movie)
            "Traveling" -> icon.setImageResource(R.drawable.activity)
            "Clothing" -> icon.setImageResource(R.drawable.clothing)
            "Housing" -> icon.setImageResource(R.drawable.housing)
            "Tuition" -> icon.setImageResource(R.drawable.tuition)
            else -> { // Note the block
            }
        }

        commentTextView.text = event.comment
    }

    override fun onClick(view: View?) {

    }

}