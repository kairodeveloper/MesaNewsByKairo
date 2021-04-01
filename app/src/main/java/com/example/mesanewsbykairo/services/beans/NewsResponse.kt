package com.example.mesanewsbykairo.services.beans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponse (
    @SerializedName("pagination")
    @Expose
    val pagination: PaginationModel? = null,

    @SerializedName("data")
    @Expose
    val data: List<NewsModel>? = null
)