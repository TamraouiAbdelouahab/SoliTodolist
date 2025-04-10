package com.example.solitodolist.data

import java.sql.ClientInfoStatus

data class Task(
    var id: Int,
    var title: String,
    var description: String,
    var creationDate: String,
    var dueDate:String,
    var tasklist_id:Int

) {
    var status: Int =0
        get() = field
        set(value) {field = value}

    var important: Int=0
        get() = field
        set(value) {field = value}

}