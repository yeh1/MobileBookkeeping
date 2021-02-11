package com.example.mobilebookkeeping

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mob.MyEvent
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class EventAdapter(var events: ArrayList<MyEvent>) : RecyclerView.Adapter<EventViewHolder>() {

    //var questions: ArrayList<MathFact> = ArrayList<MathFact>()


    private lateinit var removedEvent: MyEvent
    val eventRef = FirebaseFirestore
        .getInstance()
        .collection("events")

    init {
        eventRef.addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            for (docChange in snapshot!!.documentChanges) {
                val event = MyEvent.fromSnapshot(docChange.document)
                when (docChange.type) {
                    DocumentChange.Type.ADDED -> {
                        events.add(0, event)
                        notifyItemInserted(0)
                        Log.d("myTag","adapter: " +  this.events?.size.toString())
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
        eventRef.document(events[viewHolder.adapterPosition].id).delete()
    }

//    fun removeItemAt(position: Int) {
//        removedEvent = events[position]
//        events.remove(removedEvent)
//        notifyItemRemoved(viewHolder.adapterPosition)
//        Snackbar.make(viewHolder.itemView," + ", Snackbar.LENGTH_LONG).setAction("REVIEW LATER") {
//            //reviewMathFacts.add(removedEvent)
//        }.show()
//    }

    fun collapseEvent(position: Int){
        events.removeAll(events[position].events)
        events[position].isExpanded = false
    }

    fun expandEvent(position: Int){
        for(e in events[position].events){
            events.add(position+1, e)
        }
        events[position].isExpanded = true
        Log.d("myTag", events[position].events.toString());
    }

    fun toggleEvent(position: Int){
        if(events[position].isExpanded)
            collapseEvent(position)
        else
            expandEvent(position)
    }


    fun removeAllItem() {
        events.clear()
        notifyDataSetChanged()
    }

    fun getEventAt(position: Int): MyEvent {
        return events.get(position)
    }

    fun addNewEvent(myEvent: MyEvent){
        this.add(myEvent)
    }

    fun add(myEvent: MyEvent){
        events.add(myEvent)
        eventRef.add(myEvent)
    }

}