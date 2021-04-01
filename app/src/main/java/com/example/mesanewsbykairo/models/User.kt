package com.example.mesanewsbykairo.models

import com.orm.SugarRecord

open class User(
    val email: String = "",
    val token: String = ""
) : SugarRecord() {
}