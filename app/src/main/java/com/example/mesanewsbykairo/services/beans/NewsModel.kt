package com.example.mesanewsbykairo.services.beans
import android.view.View
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("title")
    @Expose
    val title: String? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,

    @SerializedName("content")
    @Expose
    val content: String? = null,

    @SerializedName("author")
    @Expose
    val author: String? = null,

    @SerializedName("published_at")
    @Expose
    val publishedAt: String? = null,

    @SerializedName("highlight")
    @Expose
    val highlight: Boolean? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("image_url")
    @Expose
    val imageUrl: String? = null,
    var isTitle: Boolean = false,
    var isANew: Boolean = true
)