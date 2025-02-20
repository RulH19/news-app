package com.example.newsapp.data.model

import com.example.newsapp.data.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("to", encoded = true) to: String? = null,
        @Query("sortBy") sortBy: String
    ): NewsResponse

    @GET("everything")
    suspend fun getAllLasSixMonth(
        @Query("domains") query: String
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getHeadlinesUSNow(
        @Query("country") query: String,
        @Query("category") sortBy: String
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getHeadlinesTechCrunch(
        @Query("sources") query: String,
    ): NewsResponse
}