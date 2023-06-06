package com.mmm.buget_trackerapp.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.mmm.buget_trackerapp.Database.DBHelper
import com.mmm.buget_trackerapp.Model.TransModel
import com.mmm.buget_trackerapp.databinding.FragmentSumrrayBinding

class SumrrayFragment : Fragment() {


    lateinit var dbHelper : DBHelper
    lateinit var binding: FragmentSumrrayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSumrrayBinding.inflate(layoutInflater)

        dbHelper = DBHelper(context)

       var list = dbHelper.getTransaction()




      total(list)


        return binding.root


    }

    private fun total(list: ArrayList<TransModel>) {


        var totalin = 0
        var totalex = 0
        for (trans in list){

            if (trans.isExpense == 0){
                totalin +=  trans.amt

            }else{
                totalex += trans.amt
            }

        }

        binding.txtin.text = totalin.toString()
        binding.txtex.text = totalex.toString()
        binding.txtsum.text = (totalin - totalex).toString()


        Chart(totalin,totalex)



    }

    private fun Chart(totalin: Int, totalex: Int) {
        val pieEntries = ArrayList<PieEntry>()
        val label = "type"


        //initializing data
        val typeAmountMap: MutableMap<String, Int> = HashMap()

        typeAmountMap["Income"] = totalin
        typeAmountMap["Expense"] = totalex


        //initializing colors for the entries
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#f57171"))
        colors.add(Color.parseColor("#309967"))

        //input data and fit data into pie chart entry
        for (type in typeAmountMap.keys) {
            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
        }

        //collecting the entries with label name
        val pieDataSet = PieDataSet(pieEntries, label)
        //setting text size of the value
        pieDataSet.valueTextSize = 12f
        //providing color list for coloring different entries
        pieDataSet.colors = colors
        //grouping the data set from entry to chart
        val pieData = PieData(pieDataSet)
        //showing the value of the entries, default true if not set
        pieData.setDrawValues(true)
        binding.piechart.setData(pieData)
        binding.piechart.invalidate()
    }



//    private fun updatesum(translist: ArrayList<TransModel>) {
//
//        var totalincome = 0
//        var totalExpense = 0
//        for (trans in translist){
//
//            if (trans.isExpense == 0){
//                totalincome +=  trans.amt
//
//            }else{
//                totalExpense += trans.amt
//            }
//
//        }
//
//        binding.txtin.text = totalincome.toString()
//        binding.txtex.text = totalExpense.toString()
//        binding.txtsum.text = (totalincome - totalExpense).toString()
//
//
//
//        chart(totalincome,totalExpense)
//
//    }
//
//    private fun chart(totalincome: Int, totalExpense: Int) {
//        val pieEntries = ArrayList<PieEntry>()
//        val label = "type"
//
//
//        //initializing data
//        val typeAmountMap: MutableMap<String, Int> = HashMap()
//
//        typeAmountMap["Income"] = totalincome
//        typeAmountMap["Expense"] = totalExpense
//
//
//        //initializing colors for the entries
//        val colors = ArrayList<Int>()
//        colors.add(Color.parseColor("#f57171"))
//        colors.add(Color.parseColor("#309967"))
//
//        //input data and fit data into pie chart entry
//        for (type in typeAmountMap.keys) {
//            pieEntries.add(PieEntry(typeAmountMap[type]!!.toFloat(), type))
//        }
//
//        //collecting the entries with label name
//        val pieDataSet = PieDataSet(pieEntries, label)
//        //setting text size of the value
//        pieDataSet.valueTextSize = 12f
//        //providing color list for coloring different entries
//        pieDataSet.colors = colors
//        //grouping the data set from entry to chart
//        val pieData = PieData(pieDataSet)
//        //showing the value of the entries, default true if not set
//        pieData.setDrawValues(true)
//        binding.piechart.setData(pieData)
//        binding.piechart.invalidate()
//
//
//    }
//

}