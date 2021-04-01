package com.example.mesanewsbykairo.services.beans

import com.google.gson.annotations.SerializedName

data class SignUpBody (
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("password")
    val password: String? = null
)
