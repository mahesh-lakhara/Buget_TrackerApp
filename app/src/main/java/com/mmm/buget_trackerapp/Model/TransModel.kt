package com.mmm.buget_trackerapp.Model

class TransModel {


    var id = 0
    var amt = 0
    lateinit var title: String
    lateinit var note : String
    var isExpense = 0

    constructor(
        id: Int, amt: Int, title: String, note: String, isExpense: Int) {
        this.id = id
        this.amt = amt
        this.title = title
        this.note = note
        this.isExpense = isExpense
    }
}

