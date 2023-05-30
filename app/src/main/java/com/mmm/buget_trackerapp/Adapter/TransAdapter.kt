package com.mmm.buget_trackerapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mmm.buget_trackerapp.Model.TransModel
import com.mmm.buget_trackerapp.R
import com.mmm.buget_trackerapp.databinding.TransctiomIteamBinding

class TransAdapter(update : (TransModel) -> Unit,delete :(Int)->Unit) : RecyclerView.Adapter<TransAdapter.TransHolder>() {

    var update = update
    var delete = delete
    var transList = ArrayList<TransModel>()
    lateinit var context: Context

    class TransHolder(itemView: TransctiomIteamBinding) : ViewHolder(itemView.root) {
        var binding = itemView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransHolder {
        context = parent.context
        var binding =
            TransctiomIteamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransHolder(binding)

    }

    override fun getItemCount(): Int {
        return transList.size

    }

    override fun onBindViewHolder(holder: TransHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.binding.apply {

            transList.get(position).apply {
                txttitle.text = title
                txtnote.text = note
                txtamt.text = amt.toString()

                if (isExpense == 0) {
                    txtamt.setTextColor(Color.GREEN)
                    backgroun.setImageResource(R.drawable.up_transction)
                    imgdown.setImageResource(R.drawable.up)
                } else {
                    txtamt.setTextColor(Color.RED)
                    backgroun.setImageResource(R.drawable.down_transction)
                    imgdown.setImageResource(R.drawable.down)
                }

            }

        }

        holder.itemView.setOnLongClickListener(object : OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {

                var popupMenu = PopupMenu(context,holder.itemView)
                popupMenu.menuInflater.inflate(R.menu.trans_menu,popupMenu.menu)

                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
                    override fun onMenuItemClick(p0: MenuItem?): Boolean {

                        if (p0?.itemId == R.id.update) {
                            update.invoke(transList.get(position))

                        }

                        if (p0?.itemId == R.id.delete) {
                            delete.invoke(transList.get(position).id)

                        }

                        return true
                    }

                })

                popupMenu.show()



                return false
            }


        })

    }

    fun setTrans(translist: ArrayList<TransModel>) {
        this.transList = translist

    }

    fun updateData(transaction: ArrayList<TransModel>) {
        transList = transaction
        notifyDataSetChanged()

    }

}