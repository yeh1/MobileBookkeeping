package com.example.mobilebookkeeping

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.mobilebookkeeping.EventAdapter
import com.example.mobilebookkeeping.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.add_event.*
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.dialog_add_event.view.*
import java.util.*

class NewEventFragment(var adapter: EventAdapter) : Fragment(), EventProvider {

    lateinit var eventProvider: EventProvider
    //val adapter = EventAdapter(ArrayList())
    val transactionFragment = TransactionFragment(adapter)
    private val date = Date()
    var mapOfEvent = HashMap<String, MyEvent>()
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
            val newDateEvent = MyEvent(0, "", true)
            //newDateEvent.title = date.toString().substring(0,10)
            val newEvent = MyEvent(amount.toInt(), user_comment, false)
            if(toggle_button.isChecked){
                newEvent.isExpense = false
            }
           // Log.d("myTag","no q: " + eventRef.)

            if(mapOfEvent.containsKey(date.toString().substring(0,10))){
                mapOfEvent.get(date.toString().substring(0,10))?.events?.add(newEvent)
                Log.d("myTag","equal:"+ mapOfEvent.get(date.toString().substring(0,10))?.events?.toString())

            }else{
                //val newEvent = MyEvent(amount.toInt(), user_comment, false)
                mapOfEvent.put(date.toString().substring(0,10),newEvent)
                //adapter.addNewEvent(newEvent)
                mapOfEvent.get(date.toString().substring(0,10))?.events?.add(newEvent)
                Log.d("myTag","no q: " )
            }

            eventProvider.sendEvents(newDateEvent)
        }

        builder?.setNegativeButton(android.R.string.cancel, null)
        builder?.create()?.show()
    }

    fun addNewEvent(){

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
        myView.done_button.setOnClickListener {
            showConfirmDialog(myView, amount.text.toString(), user_comment.text.toString())
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