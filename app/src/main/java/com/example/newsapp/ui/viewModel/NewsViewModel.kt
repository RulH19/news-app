package com.example.newsapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.data.response.Article
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository): ViewModel() {

    private val _news = MutableLiveData<List<Article>>()
    val newList: LiveData<List<Article>> get() = _news

    private val _headline = MutableLiveData<List<Article>>()
    val headlineList: LiveData<List<Article>> get() = _headline

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchNews(query: String, from: String, to: String?, sortBy: String) {

        viewModelScope.launch {
            try {
                val response = repository.getNews(query, from, to, sortBy)
                if (response.status == "ok") {
                    _news.postValue(response.articles)
                    Log.e("responseData", "fetchNews: ${response.articles}", )
                } else {
                    _error.postValue("Error: ${response.status}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchAllLasSixMonth(domain: String) {
        viewModelScope.launch {
            try {
                val response = repository.getAllLasSixMonth(domain)
                if (response.status == "ok") {
                    _headline.postValue(response.articles)
                    Log.e("responseData", "fetchNews: ${response.articles}", )
                } else {
                    _error.postValue("Error: ${response.status}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchHeadlinesUSNow(country: String, category: String) {
        viewModelScope.launch {
            try {
                val response = repository.getHeadlinesUSNow(country, category)
                if (response.status == "ok") {
                    _headline.postValue(response.articles)
                    Log.e("responseData", "fetchNews: ${response.articles}", )
                } else {
                    _error.postValue("Error: ${response.status}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchHeadlinesTechCrunch(sources: String) {
        viewModelScope.launch {
            try {
                val response = repository.getHeadlinesTechCrunch(sources)
                if (response.status == "ok") {
                    _headline.postValue(response.articles)
                    Log.e("responseData", "fetchNews: ${response.articles}", )
                } else {
                    _error.postValue("Error: ${response.status}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Unknown error")
            }
        }
    }
}