package com.example.mesanewsbykairo.services.beans
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaginationModel (
    @SerializedName("current_page")
    @Expose
    val currentPage: Int? = null,

    @SerializedName("per_page")
    @Expose
    val perPage: Int? = null,

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int? = null,

    @SerializedName("total_items")
    @Expose
    val totalItems: Int? = null
)