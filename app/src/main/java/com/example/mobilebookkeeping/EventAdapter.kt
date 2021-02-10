package com.example.mobilebookkeeping

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class EventAdapter(var context: Context?, var events: ArrayList<MyEvent>) : RecyclerView.Adapter<EventViewHolder>() {

    //var questions: ArrayList<MathFact> = ArrayList<MathFact>()


    private lateinit var removedEvent: MyEvent
//    lateinit var MFP: MathFactsProvider


    override fun onCreateViewHolder(parent: ViewGroup, index: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_row_view, parent, false)
        return EventViewHolder(view, this)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(viewHolder: EventViewHolder, index: Int) {
        viewHolder.bind(events[index])
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        removedEvent = events[viewHolder.adapterPosition]
        events.remove(removedEvent)
        notifyItemRemoved(viewHolder.adapterPosition)

            Snackbar.make(viewHolder.itemView," + ", Snackbar.LENGTH_LONG).setAction("REVIEW LATER") {
                //reviewMathFacts.add(removedEvent)
            }.show()
    }

    fun removeAllItem() {
            events.clear()
        notifyDataSetChanged()
    }

    fun getEventAt(position: Int): MyEvent {
        return events.get(position)
    }

}