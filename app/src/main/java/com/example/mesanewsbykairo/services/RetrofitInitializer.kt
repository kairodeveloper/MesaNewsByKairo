package com.example.mesanewsbykairo.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    private val retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL_API)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

    fun newsService() : NewsService {
        return retrofit.create(NewsService::class.java)
    }

}