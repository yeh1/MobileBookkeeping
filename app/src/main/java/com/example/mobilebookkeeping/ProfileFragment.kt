package com.example.mobilebookkeeping

import android.os.Bundle
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_profile, container, false)
        myView.sign_out_button.setOnClickListener{
            val activity: MainActivity? = activity as MainActivity?
            activity?.switchToLoginFragment()

        }

        return myView
    }

//    private fun showBudgetDialog() {
//        val builder = AlertDialog.Builder(this!!.context)
//        builder.setTitle("title")
//
//        val view = LayoutInflater.from(this).inflate(R.layout.title_edit, null, false)
//        builder.setView(view)
//        view.title_name_text.setText(this.title.toString())
//        builder.setPositiveButton(android.R.string.ok) { _, _ ->
//            this.setTitle(view.title_name_text.text)
//        }
//        builder.create().show()
//    }

}