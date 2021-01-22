package com.example.mobilebookkeeping.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobilebookkeeping.R

class AddFragment : Fragment() {

    private lateinit var addViewModel: AddViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        addViewModel =
                ViewModelProvider(this).get(AddViewModel::class.java)
        val root = inflater.inflate(R.layout.add_event, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }
}