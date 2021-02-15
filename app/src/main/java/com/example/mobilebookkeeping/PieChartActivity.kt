package com.example.mobilebookkeeping

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate


class PieChartActivity: AppCompatActivity(){

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
//        setTheme(R.style.myTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pie_chart);
        val pieChart : PieChart = findViewById(R.id.layout_piechart);
        val NoOfEmp : ArrayList<PieEntry> = ArrayList()

        NoOfEmp.add(PieEntry(945f, 0F));
        NoOfEmp.add(PieEntry(1040f, 1F));
        NoOfEmp.add(PieEntry(1133f, 2F));
        NoOfEmp.add(PieEntry(1240f, 3F));
        NoOfEmp.add(PieEntry(1369f, 4F));
        val dataSet : PieDataSet = PieDataSet(NoOfEmp.toMutableList(), "Number Of Employees");

        val year : ArrayList<String> =  ArrayList()

        year.add("2008");
        year.add("2009");
        year.add("2010");
        year.add("2011");
        year.add("2017");
        val  data  = PieData(dataSet)
        pieChart.setData(data);
        //dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateXY(5000, 5000);
    }
}