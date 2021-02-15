package com.example.mob

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilebookkeeping.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var eventProvider: EventProvider
/**
 * A simple [Fragment] subclass.
 * Use the [TransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransactionFragment(var adapter: EventAdapter?) : Fragment(), EventProvider {


    //var adapter: EventAdapter? = null
    private var events: ArrayList<MyEvent>? = ArrayList()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
    init {
        Log.d("myTag", adapter?.events!!.size.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        for(e in adapter!!.events){
            if(e.isDateEvent)
                e.updateAmount()
        }
        Log.d("myTag", adapter!!.events.size.toString())
        val recyclerView = inflater.inflate(R.layout.transaction_recycler_view, container, false) as RecyclerView
        //adapter = events?.let { EventAdapter(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)
        return recyclerView
    }

    fun setEventsTo(event: MyEvent){
        this.events?.add(0, event)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            eventProvider = this
        } catch (e: ClassCastException) {
            throw ClassCastException("Error in retrieving data. Please try again");
        }
    }

    override fun sendEvents(event: MyEvent) {
        TODO("Not yet implemented")
    }


}