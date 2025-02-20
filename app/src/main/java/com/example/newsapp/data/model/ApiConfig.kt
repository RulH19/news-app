package com.example.newsapp.data.model

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {
    private const val BASE_URL = "https://newsapi.org/v2/"

    private fun getHttpClient(): OkHttpClient{
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val interceptor = Interceptor{
            val original = it.request()
            val url = original.url.newBuilder().addQueryParameter("apiKey", "275e6c782d2c43cea1c59530f45c7c70").build()
            val request = original.newBuilder().url(url).build()
            it.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun getApiService(): ApiService{
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(getHttpClient()).addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit.create(ApiService::class.java)
    }
}