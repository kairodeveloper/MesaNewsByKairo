package com.example.mesanewsbykairo.utils

import com.example.mesanewsbykairo.models.NewsDB
import com.example.mesanewsbykairo.models.User
import com.orm.SugarRecord

fun isLogged() : Boolean {
    val users = SugarRecord.findAll(User::class.java)

    var counter = 0
    users.forEach {
        counter ++
    }

    return counter > 0
}

fun getToken() : String {
    val users = SugarRecord.findAll(User::class.java)
    val list = ArrayList<User>()

    users.forEach {
        list.add(it)
    }

    return if (list.size>0) {
        list[0].token
    } else {
        ""
    }
}

fun getEmail() : String {
    val users = SugarRecord.findAll(User::class.java)
    val list = ArrayList<User>()

    users.forEach {
        list.add(it)
    }

    return if (list.size>0) {
        list[0].email
    } else {
        ""
    }
}

fun deleteAll() {
    SugarRecord.deleteAll(User::class.java)
    SugarRecord.deleteAll(NewsDB::class.java)
}