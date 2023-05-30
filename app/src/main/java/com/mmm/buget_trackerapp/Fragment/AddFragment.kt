package com.mmm.buget_trackerapp.Fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.mmm.buget_trackerapp.Database.DBHelper
import com.mmm.buget_trackerapp.Adapter.TransAdapter
import com.mmm.buget_trackerapp.Model.TransModel
import com.mmm.buget_trackerapp.R
import com.mmm.buget_trackerapp.databinding.FragmentAddBinding
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import java.sql.Date
import java.text.SimpleDateFormat


class AddFragment : Fragment() {
    private  val TAG = "AddFragment"

    lateinit var binding: FragmentAddBinding
    var isExpense = 0
    lateinit var dbHelper: DBHelper
    lateinit var adapter: TransAdapter
    lateinit var transModel: TransModel
    var SeleDate = 0
    var SeleMonth = 0
    var SeleYear = 0
   var seleTime = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(layoutInflater)



        binding.groupWeekdays.setOnCheckedChangeListener(object :
            SingleSelectToggleGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: SingleSelectToggleGroup?, checkedId: Int) {
                if (checkedId == R.id.income) {
                    isExpense = 0
                    
                } else if (checkedId == R.id.Expense) {
                    isExpense = 1

                }
            }


        })


        try {

            var list = dbHelper.getTransaction()

            var income = 0
            var expense = 0
            for (trans in list){

                if (trans.isExpense == 0){
                    income += trans.amt

                }else{
                    expense += trans.amt
                }

            }


        } catch (e : Exception){

        }


        binding.txtDate.setOnClickListener {

            var date = java.util.Date()


            var format1 = SimpleDateFormat("dd-MM-YYYY")
            var currentDate = format1.format(date)

            var dates = currentDate.split("-")
            binding.txtDate.text = currentDate

            var dialog =
                DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

                        var seleYear = p1
                        var seleMonth = p2+1
                        var seleDate = p3

                        var selectedDate = "$p3-${(p2 + 1)}-$p1"
                        binding.txtDate.text = selectedDate
                    }

                }, dates[2].toInt(), dates[1].toInt() - 1, dates[0].toInt())
            dialog.show()
        }

        binding.txtTime.setOnClickListener {
            var date = java.util.Date()


//            var format2 = SimpleDateFormat("hh:mm:ss a")

            var format2 = SimpleDateFormat("hh:mm")
            var currentTime = format2.format(date)

            binding.txtTime.text = currentTime
            var seleTime = currentTime


            var dialog1 = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {

                }

            }, 10, 0, false)

            dialog1.show()

        }


        dbHelper = DBHelper(context)



        binding.btnsave.setOnClickListener {
            var amt = binding.edtAmount.text.toString().toInt()
            var title = binding.edtTitle.text.toString()
            var note = binding.edtNote.text.toString()




            if (title.isEmpty() || note.isEmpty() || amt == 0) {

                Toast.makeText(context, "Please Enter Data.......", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Data Add Succesfullly", Toast.LENGTH_SHORT).show()

                var model = TransModel(0, amt, title, note, isExpense)

                dbHelper.addAmount(model)


                clearEditText()


            }


        }




        return binding.root
    }

    private fun clearEditText() {
        binding.edtAmount.setText("")
        binding.edtTitle.setText("")
        binding.edtNote.setText("")

    }
}





