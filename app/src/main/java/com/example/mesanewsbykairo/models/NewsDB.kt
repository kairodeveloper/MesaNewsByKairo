package com.example.mesanewsbykairo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord


open class NewsDB(
    val title: String = "",
    val description: String = "",
    val content: String = "",
    val author: String = "",
    val publishedAt: String = "",
    val highlight: Boolean = false,
    val url: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false,
    var isTitle: Boolean = false

) : SugarRecord() {

}
