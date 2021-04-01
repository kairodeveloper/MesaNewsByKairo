package com.example.mesanewsbykairo.services.beans

import com.google.gson.annotations.SerializedName

data class LoginBody(
        @SerializedName("email")
        val email: String? = null,
        @SerializedName("password")
        val password: String? = null
)