package com.example.mobilebookkeeping

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialog
import com.github.dewinjm.monthyearpicker.MonthYearPickerDialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import android.content.Context
import kotlinx.android.synthetic.main.set_budget_view.*
import kotlinx.android.synthetic.main.set_budget_view.view.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private val ARG_UID = "UID"
    private var uid: String? = null


    companion object {
        @JvmStatic
        fun newInstance( uid: String) =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
        }
//        Log.d("tag", "uid is" + uid)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val userRef = FirebaseFirestore
                .getInstance()
                .collection("user")
                .document(uid!!)

        val myView = inflater.inflate(R.layout.fragment_profile, container, false)
        val eventsRef = FirebaseFirestore
                .getInstance()
                .collection("events")
        var sum = 0
        eventsRef.addSnapshotListener{ snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
            snapshot?.forEach { doc ->
                val dateEvent = doc.toObject(MyEvent::class.java)
                dateEvent.amount

            }
        }

        userRef.get().addOnSuccessListener { querySnapshot ->
            val user = querySnapshot.toObject(User::class.java)
            myView.username_text.text = user?.username
            budget_amount.text = "Budget Amount: $" + user?.budget

        }



//            userRef.whereEqualTo("uid",uid).addSnapshotListener { querySnapshot, e ->
//            for (var i in querySnapshot){
//
//            }
//            val mq = querySnapshot.toObjects(User::class.java)
//
//            for (documentChange in querySnapshot?.documentChanges!!) {
//                val user = User.fromSnapshot(documentChange.document)
//                when (documentChange.type) {
//                    DocumentChange.Type.ADDED -> {
//                        myView.username_text.text = user.username
//                    }
//
//                    DocumentChange.Type.MODIFIED -> {
//                        myView.username_text.text = user.username
//                    }
//                }
//            }
//        }

        myView.sign_out_button.setOnClickListener{
            val activity: MainActivity? = activity as MainActivity?
            activity?.switchToLoginFragment()
        }
        myView.buget_button.setOnClickListener{
            showBudgetDialog()
        }
        myView.set_username_button.setOnClickListener {
            showUsernameDialog()
        }


        myView.month_button.setOnClickListener{
            var month = setCalendar()
//            Log.d("tag", "month "+ month)
        }


        return myView
    }

    fun setCalendar(): Int {
       //TODO: https://github.com/dewinjm/monthyear-picker

        val yearSelected: Int
        val monthSelected: Int

//Set default
        val calendar: Calendar = Calendar.getInstance()
        yearSelected = calendar.get(Calendar.YEAR)
        monthSelected = calendar.get(Calendar.MONTH)

        val dialogFragment = MonthYearPickerDialogFragment
                .getInstance(monthSelected, yearSelected)

        dialogFragment.show( (this.activity as FragmentActivity).supportFragmentManager, null)

//        dialogFragment.setOnDateSetListener(
//            MonthYearPickerDialog.OnDateSetListener() {
////            override fun onDateSet(int year, int monthOfYear) {
//                // do something
//
//            }
//        )

        return monthSelected
    }



    fun showBudgetDialog() {
        val builder = AlertDialog.Builder(context)
        builder?.setTitle("Set Your Budget")

        val monthRef = FirebaseFirestore
            .getInstance()
            .collection("events")
            .whereEqualTo("month","Feb")


        monthRef.get().addOnSuccessListener { querySnapshot ->
            val user = querySnapshot.toObjects(MyEvent::class.java)
            var monthAmount = 0
            user.forEach{
                monthAmount += it.amount
            }
            budget_remain.text = "Budget Remain: $" + monthAmount
        }


        val view = LayoutInflater.from(context).inflate(R.layout.set_budget_view, null, false)
        builder?.setView(view)
//        view.buget_text.setText(this.title.toString())
        builder?.setPositiveButton(android.R.string.ok) { _, _ ->
            val userRef = FirebaseFirestore
                .getInstance()
                .collection("user")
                .document(uid!!)
            userRef.update("budget", view.buget_text.text.toString())
            budget_amount.text = "Budget Amount: $" + view.buget_text.text.toString()

        }
        builder?.setNegativeButton(android.R.string.cancel) { _, _ ->
        }
        builder?.create()?.show()
    }



    fun showUsernameDialog() {
        val builder = AlertDialog.Builder(context)
        builder?.setTitle("Set Your Username")


        val view = LayoutInflater.from(context).inflate(R.layout.set_budget_view, null, false)
        builder?.setView(view)
        builder?.setPositiveButton(android.R.string.ok) { _, _ ->
            val userRef = FirebaseFirestore
                .getInstance()
                .collection("user")
                .document(uid!!)
            userRef.update("username", view.buget_text.text.toString())
            username_text.text = view.buget_text.text.toString()

        }
        builder?.setNegativeButton(android.R.string.cancel) { _, _ ->
        }
        builder?.create()?.show()
    }




}