package com.example.mob

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.pie_chart.*
import kotlinx.android.synthetic.main.pie_chart.view.*
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

//
//    var myPieChart : PieChart? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // TODO: Rename and change types of parameters
//        //activity?.setContentView(R.layout.pie_chart)
//        //requireActivity().findViewById<R.id.>()
//        myPieChart?.startAnimation()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        myPieChart = PieChart(context)
//
//        val myView = inflater.inflate(R.layout.pie_chart, container, false)
//        myView.tvR.setText("40")
//        myView.tvPython.setText(Integer.toString(30))
//        myView.tvCPP.setText(Integer.toString(5))
//        myView.tvJava.setText(Integer.toString(25))
//        myPieChart!!.addPieSlice(
//                PieModel(
//                        "R", myView.tvR.text.toString().toInt().toFloat(),
//                        Color.parseColor("#FFA726")))
//        myPieChart!!.addPieSlice(
//                PieModel(
//                        "Python", myView.tvPython.text.toString().toInt().toFloat(),
//                        Color.parseColor("#66BB6A")))
//        myPieChart!!.addPieSlice(
//                PieModel(
//                        "C++", myView.tvCPP.text.toString().toInt().toFloat(),
//                        Color.parseColor("#EF5350")))
//        myPieChart!!.addPieSlice(
//                PieModel(
//                        "Java", myView.tvJava.text.toString().toInt().toFloat(),
//                        Color.parseColor("#29B6F6")))
//
//        // To animate the pie chart
//
//        // To animate the pie chart
//        myPieChart!!.startAnimation()
//
//        return myView
//    }

}