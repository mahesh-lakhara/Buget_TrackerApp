package com.mmm.buget_trackerapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mmm.buget_trackerapp.Model.TransModel

class DBHelper(
    context: Context?,

) : SQLiteOpenHelper(context, "BugetTracker.db", null, 1) {
    val TABLE_NAME = "trans"

    private val TAG = "DBHelper"

    override fun onCreate(p0: SQLiteDatabase?) {
        var sql = "CREATE TABLE "+TABLE_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT,amt INTEGER,title TEXT,note TEXT,isexpense INTEGER,date INTEGER)"
        p0?.execSQL(sql)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addAmount(transModel: TransModel){

        var db:SQLiteDatabase = writableDatabase
        var values = ContentValues().apply {

            put("amt",transModel.amt)
            put("title",transModel.title)
            put("note",transModel.note)
            put("isexpense",transModel.isExpense)

        }

        db.insert(TABLE_NAME,null,values)

    }




    fun getTransaction(): ArrayList<TransModel> {
        var transList = ArrayList<TransModel>()
        var db :SQLiteDatabase = readableDatabase
        var sql = "SELECT * FROM $TABLE_NAME "
        var cursor:Cursor = db.rawQuery(sql,null)
        cursor.moveToNext()

        for (i in 0..cursor.count-1){

            var id = cursor.getInt(0)
            var amt = cursor.getInt(1)
            var title = cursor.getString(2)
            var note = cursor.getString(3)
            var isExpense = cursor.getInt(4)

            var model = TransModel(id, amt, title, note, isExpense)

            Log.e(TAG, "getTransaction: ------------ "+title )
            transList.add(model)
            cursor.moveToNext()

        }
        return transList

    }

    fun updateTrans(transModel: TransModel) {
        var db = writableDatabase
        var values = ContentValues().apply {
            transModel.apply {
                put("amt",amt)
                put("title",title)
                put("note",note)
                put("isexpense",isExpense)

            }
        }
        db.update(TABLE_NAME,values,"id=${transModel.id}",null)
    }

    fun deleteTrans(id: Int) {
        var db = writableDatabase
        db.delete(TABLE_NAME,"id=$id",null)
    }



}