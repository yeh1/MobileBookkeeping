package com.example.mob

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.add_event.*
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.dialog_add_event.view.*
import java.util.*

class NewEventFragment(var adapter: EventAdapter, val isNew: Boolean) : Fragment(), EventProvider {

    lateinit var eventProvider: EventProvider
    //val adapter = EventAdapter(ArrayList())
    var editPosition = -1
    val transactionFragment = TransactionFragment(adapter)
    private val date = Date()
    val eventRef = FirebaseFirestore
        .getInstance()
        .collection("events")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun showConfirmDialog(view: View, amount: String, user_comment: String){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(R.string.add_dialog_confirm)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null, false)
        builder?.setView(view)
        view.confirm_message.setText("Amount:" + amount+ user_comment)

        builder?.setPositiveButton(android.R.string.ok){ _, _ ->
            val latestEvent = adapter.getLatestDateEvent()
            val newDate = MyEvent(0, "", true)
            val newEvent = MyEvent(amount.toInt(), user_comment, false, !toggle_button.isChecked)
            newEvent.category = category_textview.text.toString()
            if(newDate.title == latestEvent.title){
                latestEvent.events.add(newEvent)
                if(latestEvent.isExpanded)
                    adapter.events.add(1, newEvent)

                eventRef.document(latestEvent.id).update("events", latestEvent.events)
                adapter.notifyDataSetChanged()
                latestEvent.updateAmount()
            }else{
                newDate.events.add(newEvent)
                adapter.add(newDate)
                //adapter.notifyDataSetChanged()
            }
            if(toggle_button.isChecked){
                newEvent.isExpense = false
            }
        }
        builder?.setNegativeButton(android.R.string.cancel, null)
        builder?.create()?.show()
    }

    private fun showEditConfirmDialog(position: Int, amount: String, user_comment: String){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(R.string.add_dialog_confirm)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null, false)
        builder?.setView(view)
        view.confirm_message.setText("Amount:" + amount+ user_comment)
        var positionInEvents = -1
        var parentPosition = 0
        for(i in 0.. position){
            positionInEvents++
            if(adapter.events[i].isDateEvent){
                positionInEvents = -1
                parentPosition = i
            }
        }

        builder?.setPositiveButton(android.R.string.ok){ _, _ ->
            val newEvent = MyEvent(amount.toInt(), user_comment, false, !toggle_button.isChecked)
            if(toggle_button.isChecked){
                newEvent.isExpense = false
            }
            newEvent.category = category_textview.text.toString()
            val parentEvent = adapter.events[parentPosition]
            parentEvent.events[parentEvent.events.size - 1 - positionInEvents] = newEvent
            adapter.events[position] = newEvent
            parentEvent.id.let { eventRef.document(it) }
                .update("events", parentEvent.events)
            parentEvent.isExpanded = true
            parentEvent.updateAmount()
        }
        builder?.setNegativeButton(android.R.string.cancel, null)
        builder?.create()?.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            eventProvider = this
        } catch (e: ClassCastException) {
            throw ClassCastException("Error in retrieving data. Please try again");
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val myView = inflater.inflate(R.layout.add_event, container, false)
        if(isNew){
            myView.add_event_title.text = "Creating New Event"
        }else{
            myView.add_event_title.text = "Editing Event"
            myView.amount.hint = adapter.events[editPosition].amount.toString()
            myView.user_comment.hint = adapter.events[editPosition].comment
        }
        myView.done_button.setOnClickListener {
            if(isNew)
                showConfirmDialog(myView, amount.text.toString(), user_comment.text.toString())
            else
                showEditConfirmDialog(editPosition, amount.text.toString(), user_comment.text.toString())
        }
        return myView


    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment NewEventFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            NewEventFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

    override fun sendEvents(event: MyEvent) {
        transactionFragment.setEventsTo(event)
    }
}