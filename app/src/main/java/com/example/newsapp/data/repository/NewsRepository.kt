package com.example.newsapp.data.repository

import com.example.newsapp.data.model.ApiService
import com.example.newsapp.data.response.NewsResponse

class NewsRepository(private val apiService: ApiService) {

    suspend fun getNews(query: String, from: String, to: String? = null, sortBy: String ): NewsResponse =
        apiService.getNews(query, from, to, sortBy)


    suspend fun getAllLasSixMonth(domain: String): NewsResponse =
        apiService.getAllLasSixMonth(domain)

    suspend fun getHeadlinesUSNow(country: String, category: String): NewsResponse =
        apiService.getHeadlinesUSNow(country, category)

    suspend fun  getHeadlinesTechCrunch(sources: String): NewsResponse = apiService.getHeadlinesTechCrunch(sources)

}