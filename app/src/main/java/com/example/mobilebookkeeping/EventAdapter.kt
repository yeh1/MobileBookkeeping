package com.example.mobilebookkeeping

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot


class EventAdapter(var events: ArrayList<MyEvent>) : RecyclerView.Adapter<EventViewHolder>() {

    var transFragment = TransactionFragment(this)


    private lateinit var removedEvent: MyEvent
    val eventRef = FirebaseFirestore
        .getInstance()
        .collection("events")

    init {
        eventRef.orderBy("date").addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
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
        if(!events[viewHolder.adapterPosition].isDateEvent) {
            var positionInEvents = -1
            var parentPosition = 0
            for (i in 0..viewHolder.adapterPosition) {
                positionInEvents++
                if (events[i].isDateEvent) {
                    positionInEvents = -1
                    parentPosition = i
                }
            }
            removedEvent = events[viewHolder.adapterPosition]
            val parentEvent = events[parentPosition]
            parentEvent.events.remove(removedEvent)
            events.remove(removedEvent)
            notifyItemRemoved(viewHolder.adapterPosition)
            Snackbar.make(viewHolder.itemView, " + ", Snackbar.LENGTH_LONG)
                .setAction("REVIEW LATER") {
                    //reviewMathFacts.add(removedEvent)
                }.show()
            parentEvent.id.let { eventRef.document(it) }
                .update("events", parentEvent.events)
            parentEvent.updateAmount()
        }
    }



    fun collapseEvent(position: Int){
        Log.d("myTag", events[position].events.size.toString())
        events.removeAll(events[position].events)
        events[position].isExpanded = false
        //notifyDataSetChanged()
    }

    fun expandEvent(position: Int){
        for(e in events[position].events){
            events.add(position+1, e)
            notifyItemInserted(position+1)
        }
        events[position].isExpanded = true
        events[position].id.let { eventRef.document(it) }
            .update("isExpanded", true)
        Log.d("myTag", events[position].isExpanded.toString())
    }

    fun toggleEvent(position: Int){
        if(events[position].isDateEvent){
            if(events[position].isExpanded) {
                collapseEvent(position)
            }
            else {
                collapseEvent(position)
                expandEvent(position)
            }
        }else{
            val nextFrag = NewEventFragment(this, false)
            nextFrag.editPosition = position
            val ft : FragmentTransaction = (transFragment.activity as FragmentActivity).supportFragmentManager.beginTransaction()
            ft.replace(R.id.fragment_container,nextFrag)
            ft.commit()
        }
    }

    fun getLatestDateEvent(): MyEvent{
        var latest = events[0]
        for(e in events){
            if (e.isDateEvent){
                if(e.date.after(latest.date))
                    latest = e
            }
        }
        return latest
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
        //events.add(0, myEvent)
        eventRef.add(myEvent)
        eventRef.orderBy("title")
    }

}