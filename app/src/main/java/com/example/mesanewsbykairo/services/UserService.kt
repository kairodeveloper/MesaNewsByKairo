package com.example.mesanewsbykairo.services

import com.example.mesanewsbykairo.services.beans.LoginBody
import com.example.mesanewsbykairo.services.beans.LoginResponse
import com.example.mesanewsbykairo.services.beans.NewsResponse
import com.example.mesanewsbykairo.services.beans.SignUpBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST(URL_SIGN_IN)
    fun login(
        @Header("Content-Type") contentType: String,
        @Body loginBody: LoginBody
    ) : Call<LoginResponse>

    @POST(URL_SIGN_UP)
    fun signUp(
        @Header("Content-Type") contentType: String,
        @Body signupBody: SignUpBody
    ) : Call<LoginResponse>

}