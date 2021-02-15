package com.example.mobilebookkeeping

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.eazegraph.lib.charts.PieChart


class PieChartActivity: AppCompatActivity(){

    @SuppressLint("SetTextI18n")
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.myTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pie_chart);
        val pieChart : PieChart = findViewById(R.id.layout_piechart);
        val pieEntry : ArrayList<PieEntry> = ArrayList()
        val b = intent.extras
        var allCategory = ArrayList<String>()
        var amounts = ArrayList<Int>()
        if (b != null) {
            allCategory = b.getStringArrayList("allCategory") as ArrayList<String>
            amounts= b.getIntegerArrayList("amounts") as ArrayList<Int>
        }else{
            Log.d("myTag", "sb?")
        }
        for(i in 0 until amounts.size){
            pieEntry.add(PieEntry(amounts[i].toFloat(), allCategory[i]))
        }

        val t1: TextView = findViewById(R.id.pieChartTextView1)
        val t2: TextView = findViewById(R.id.pieChartTextView2)
        val t3: TextView = findViewById(R.id.pieChartTextView3)
        val t4: TextView = findViewById(R.id.pieChartTextView4)

        val i1: ImageView = findViewById(R.id.pieChartImage1)
        val i2: ImageView = findViewById(R.id.pieChartImage2)
        val i3: ImageView = findViewById(R.id.pieChartImage3)
        val i4: ImageView = findViewById(R.id.pieChartImage4)

        t1.setText(allCategory[getTopOneindex(amounts)] +": " + amounts[getTopOneindex(amounts)])
        t2.setText(allCategory[getTopTwoindex(amounts)] +": " + amounts[getTopTwoindex(amounts)])
        t3.setText(allCategory[getTopThreeindex(amounts)] +": " + amounts[getTopThreeindex(amounts)])
        t4.setText(allCategory[getTopFourindex(amounts)] +": " + amounts[getTopFourindex(amounts)])

        i1.setImageResource(getImageForTextView(t1))
        i2.setImageResource(getImageForTextView(t2))
        i3.setImageResource(getImageForTextView(t3))
        i4.setImageResource(getImageForTextView(t4))


        val dataSet  = PieDataSet(pieEntry.toMutableList(), "Percentage");
        val  data  = PieData(dataSet)
        data.setValueTextSize(25f)
        pieChart.setData(data)
        pieChart.setUsePercentValues(true)
        dataSet.colors =  ColorTemplate.COLORFUL_COLORS.toList()
        pieChart.animateXY(5000, 5000)
    }

    fun getTopOneindex(list: ArrayList<Int>): Int{
        var index = 0
        for(i in 0 until list.size){
            if(list[i] > list[index])
                index = i
        }
        return index
    }


    fun getTopTwoindex(intArray: ArrayList<Int>): Int {
        var largest = intArray[0]
        var largest2: Int? = null
        var maxIndex = 0
        var maxIndex2 = 0
        for (i in 0 until intArray.size) {
            if (largest < intArray[i]) {
                largest2 = largest
                maxIndex2 = maxIndex
                largest = intArray[i]
                maxIndex = i
            } else if (largest2 == null || intArray[i] > largest2) {
                largest2 = intArray[i]
                maxIndex2 = i
            }
        }
        return maxIndex2
    }

    fun getTopThreeindex(intArray: ArrayList<Int>): Int {
        val secondLargest = getTopTwoindex(intArray)
        val list = ArrayList<Int>()
        for(num in intArray){
            if(num < intArray[secondLargest])
                list.add(num)
        }
        Log.d("myTag", intArray.toString())
        Log.d("myTag", list.toString())
        val thirdlargest = list[getTopOneindex(list)]
        Log.d("myTag", thirdlargest.toString())
        for(i in 0 until  intArray.size){
            if(thirdlargest == intArray[i])
                return i
        }
        return 0
    }

    fun getTopFourindex(intArray: ArrayList<Int>): Int {

        val thirdLargest = getTopThreeindex(intArray)
        val list = ArrayList<Int>()
        for(num in intArray){
            if(num < intArray[thirdLargest]){
                list.add(num)
            }
        }
        val fourthlargest = list[getTopOneindex(list)]
        for(i in 0 until  intArray.size){
            if(fourthlargest == intArray[i]) {
                return i
            }
        }
        return 0
    }

    fun getImageForTextView(t: TextView) :Int{
        when (t.text.substring(0..2)) {
            "Foo" -> return R.drawable.meal
            "Act" -> return R.drawable.movie
            "Tra" -> return R.drawable.activity
            "Clo" -> return R.drawable.clothing
            "Hou" -> return R.drawable.housing
            "Tui" -> return R.drawable.tuition
            else -> { // Note the block
            }
        }
        return R.drawable.fuel
    }

}