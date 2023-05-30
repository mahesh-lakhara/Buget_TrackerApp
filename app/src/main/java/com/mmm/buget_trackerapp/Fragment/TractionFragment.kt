package com.mmm.buget_trackerapp.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmm.buget_trackerapp.Adapter.TransAdapter
import com.mmm.buget_trackerapp.Database.DBHelper
import com.mmm.buget_trackerapp.Model.TransModel
import com.mmm.buget_trackerapp.databinding.FragmentTractionBinding
import com.mmm.buget_trackerapp.databinding.UpdateDialogBinding

class TractionFragment : Fragment() {

    var translist = ArrayList<TransModel>()
    lateinit var binding : FragmentTractionBinding
    var isExpense = 0
    lateinit var adapter : TransAdapter
    lateinit var dbHelper: DBHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTractionBinding.inflate(layoutInflater)

        dbHelper = DBHelper(context)
        updateTotal(translist)
        translist = dbHelper.getTransaction()
        translist = translist.reversed() as ArrayList<TransModel>

        adapter = TransAdapter( {

          updateDialog(it)


        },{


            deleteTrans(it)
        })
        adapter.setTrans(translist)

        binding.rcvtranslist.layoutManager = LinearLayoutManager(context)
        binding.rcvtranslist.adapter = adapter

        return binding.root
    }

    private fun deleteTrans(it: Int) {
        var dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete Trasction")
            .setMessage("Are You want to Delete?")
            .setPositiveButton("Yes", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    dbHelper.deleteTrans(it)
                    updateTotal(dbHelper.getTransaction())
                    try {

                        adapter.updateData(dbHelper.getTransaction().reversed() as ArrayList<TransModel>)

                    }catch (e: Exception){

                    }

                }
            }).setNegativeButton("No", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {


                }


            }).create()
        dialog.show()

    }

    fun updateTotal(translist: ArrayList<TransModel>) {

        var totalincome = 0
        var totalExpense = 0
        for (trans in translist){

            if (trans.isExpense == 0){
                totalincome +=  trans.amt

            }else{
                totalExpense += trans.amt
            }

        }

        binding.txtincome.text = totalincome.toString()
        binding.txtExpense.text = totalExpense.toString()
        binding.allover.text = (totalincome - totalExpense).toString()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun updateDialog(transModel: TransModel) {
        var dialog = Dialog(requireContext())
        var bind = UpdateDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)


        bind.edtAmount.setText(transModel.amt.toString())
        bind.edtTitle.setText(transModel.title)
        bind.edtNote.setText(transModel.note)


        bind.btnsave.setOnClickListener {
            var amt = bind.edtAmount.text.toString().toInt()
            var title = bind.edtTitle.text.toString()
            var note = bind.edtNote.text.toString()
            var model = TransModel(transModel.id, amt, title, note, transModel.isExpense)

                dbHelper.updateTrans(model)
            dialog.dismiss()
            adapter.updateData(dbHelper.getTransaction().reversed() as ArrayList<TransModel>)
            updateTotal(dbHelper.getTransaction())


        }

        dialog.show()
    }


}