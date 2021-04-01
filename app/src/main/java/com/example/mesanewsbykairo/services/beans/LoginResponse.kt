package com.example.mesanewsbykairo.services.beans

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
@SerializedName("token")
@Expose
val token: String? = null
)