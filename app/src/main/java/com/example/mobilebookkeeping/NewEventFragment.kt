package com.example.mobilebookkeeping

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.mobilebookkeeping.category.CategoryFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.add_event.*
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.dialog_add_event.view.*
import java.util.*

class NewEventFragment(var adapter: EventAdapter, val isNew: Boolean) : Fragment(), EventProvider {

    lateinit var eventProvider: EventProvider
    //val adapter = EventAdapter(ArrayList())
    var editPosition = -1

    var transactionFragment = TransactionFragment(adapter)
    private val date = Date()
    val cateFragment = CategoryFragment(adapter)
    val eventRef = FirebaseFirestore
            .getInstance()
            .collection("events")
    private val ARG_UID = "UID"
    private var uid: String? = null


//    private var listener: OnSelectedListener? = null
    companion object {
        @JvmStatic
        fun newInstance( adapter: EventAdapter,  isNew: Boolean, uid: String) =
                NewEventFragment(adapter, isNew ).apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)
                    }
                }
    }

    init {
        adapter.eventAdapterEditPosition = editPosition
        adapter.isNew = isNew
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
        }
    }

    private fun showConfirmDialog(view: View, amount: String, user_comment: String){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(R.string.add_dialog_confirm)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null, false)
        builder?.setView(view)
        view.confirm_category.setText("Category: " + category_button.text.toString())
        view.confirm_amount.setText("Amount:" + amount)
        view.confirm_comment.setText("Comment: " + user_comment)
        builder?.setPositiveButton(android.R.string.ok){ _, _ ->
            val latestEvent = adapter.getLatestDateEvent()
            val newDate = MyEvent(0, "", true, uid!!)
            val newEvent = MyEvent(amount.toInt(), user_comment, false, uid!!, !toggle_button.isChecked)
            newEvent.category = category_button.text.toString()
            if(newDate.title == latestEvent.title){
                latestEvent.events.add(newEvent)
                if(latestEvent.isExpanded)
                    adapter.events.add(1, newEvent)

                latestEvent.updateAmount()
                eventRef.document(latestEvent.id).update("events", latestEvent.events)
                eventRef.document(latestEvent.id).update("amount", latestEvent.amount)
                eventRef.document(latestEvent.id).update("income", latestEvent.income)
                adapter.notifyDataSetChanged()

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
        view.confirm_category.setText("Category: " + category_button.text.toString())
        view.confirm_amount.setText("Amount:" + amount)
        view.confirm_comment.setText("Comment: " + user_comment)
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
            val newEvent = MyEvent(amount.toInt(), user_comment, false, uid!!, !toggle_button.isChecked)
            if(toggle_button.isChecked){
                newEvent.isExpense = false
            }
            newEvent.category = category_button.text.toString()
            //adapter.category.amount += newEvent.amount TODO
            Log.d("myTag", adapter.category)
//            adapter.category.id.let { eventRef.document(it) }
//                .update("amount", adapter.category.amount)
            val parentEvent = adapter.events[parentPosition]
            parentEvent.events[parentEvent.events.size - 1 - positionInEvents] = newEvent
            adapter.events[position] = newEvent
            parentEvent.isExpanded = true
            parentEvent.updateAmount()
            parentEvent.id.let { eventRef.document(it) }
                    .update("events", parentEvent.events)
            parentEvent.id.let { eventRef.document(it) }
                    .update("amount", parentEvent.amount)
            parentEvent.id.let { eventRef.document(it) }
                    .update("income", parentEvent.income)
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
            myView.category_button.setText(adapter.name)
        }else{
            myView.add_event_title.text = "Editing Event"
            myView.amount.setText(adapter.events[editPosition].amount.toString())
            myView.user_comment.setText(adapter.events[editPosition].comment)
            myView.category_button.setText(adapter.name)

        }
        myView.done_button.setOnClickListener {
            if(isNew)
                showConfirmDialog(myView, amount.text.toString(), user_comment.text.toString())
            else
                showEditConfirmDialog(editPosition, amount.text.toString(), user_comment.text.toString())
        }

        myView.category_button.setOnClickListener{
            val ft : FragmentTransaction = (this.activity as FragmentActivity).supportFragmentManager.beginTransaction()

            adapter.eventAdapterEditPosition = editPosition
            ft.replace(R.id.fragment_container, CategoryFragment(adapter))
            ft.commit()
            category_button.text = cateFragment.name
        }

        return myView


    }

    override fun sendEvents(event: MyEvent) {
        transactionFragment.setEventsTo(event)
    }
}