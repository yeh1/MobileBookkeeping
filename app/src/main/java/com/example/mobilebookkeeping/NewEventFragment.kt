package com.example.mobilebookkeeping

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.add_event.*
import kotlinx.android.synthetic.main.add_event.view.*
import kotlinx.android.synthetic.main.dialog_add_event.*
import kotlinx.android.synthetic.main.dialog_add_event.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewEventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewEventFragment : Fragment(), EventProvider {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var eventProvider: EventProvider
    val transactionFragment = TransactionFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }
    private fun showConfirmDialog(view: View, amount: String, user_comment: String){
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setTitle(R.string.add_dialog_confirm)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_event, null, false)
        builder?.setView(view)
        view.confirm_message.setText("Amount:" + amount+ user_comment)

        builder?.setPositiveButton(android.R.string.ok){ _, _ ->
            val newEvent = MyEvent(amount.toInt(), user_comment)
            eventProvider.sendEvents(newEvent)
            Log.d("myTag", "event sent" + amount + user_comment);
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