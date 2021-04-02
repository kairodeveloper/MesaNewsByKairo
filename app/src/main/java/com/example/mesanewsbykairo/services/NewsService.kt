package com.example.mesanewsbykairo.services

import com.example.mesanewsbykairo.services.beans.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsService {
    @GET(URL_NEWS)
    fun getNews(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") token: String,
        @Query("current_page") currentPage: Int
    ) : Call<NewsResponse>


    @GET(URL_NEWS)
    fun getNewsByPublishedData(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") token: String,
        @Query("current_page") currentPage: Int,
        @Query("published_at") publishedAt: String
    ) : Call<NewsResponse>
}