package com.example.newsapp.ui.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.model.ApiConfig
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.ui.adapter.ItemHeadlineAdapter
import com.example.newsapp.ui.adapter.ItemNewsAdapter
import com.example.newsapp.ui.view.bookmark.SaveNewsActivity
import com.example.newsapp.ui.view.detail.DetailActivity
import com.example.newsapp.ui.viewModel.NewsViewModel
import com.example.newsapp.ui.viewModel.NewsViewModelFactory

class MainActivity : AppCompatActivity() {
    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepository(ApiConfig.getApiService()))
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: ItemNewsAdapter

    private lateinit var recyclerViewHeadline: RecyclerView
    private lateinit var itemHeadlineAdapter: ItemHeadlineAdapter
    private var activeNewsButton: Button? = null
    private var activeHeadlineButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerViewHeadline = findViewById(R.id.recyclerViewHeadline)
        recyclerViewHeadline.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        val btnRecent = findViewById<Button>(R.id.btn_recent)
        val btnPopularity = findViewById<Button>(R.id.btn_popularity)
        val btnTechcrunch = findViewById<Button>(R.id.btn_techrunch)
        val btnUS = findViewById<Button>(R.id.btn_us)

        setupButton(btnRecent)
        setupButton(btnPopularity)
        setupButton(btnTechcrunch)
        setupButton(btnUS)

        newsViewModel.fetchNews("apple", "2025-02-18", null, "popularity")
        setActiveNewsButton(btnPopularity)
        newsViewModel.newList.observe(this) { articles ->
            if (articles.isNotEmpty()) {
                newsAdapter = ItemNewsAdapter(articles) { article ->
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("ARTICLE_URL", article)
                    startActivity(intent)
                }
                recyclerView.adapter = newsAdapter
            } else {
                Toast.makeText(this, "Tidak ada berita", Toast.LENGTH_SHORT).show()
            }
        }
        newsViewModel.fetchHeadlinesTechCrunch("techcrunch")
        setActiveHeadlineButton(btnTechcrunch)
        newsViewModel.headlineList.observe(this) { articles ->
            if (articles.isNotEmpty()) {
                itemHeadlineAdapter = ItemHeadlineAdapter(articles) { article ->
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra("ARTICLE_URL", article)
                    startActivity(intent)
                }
                recyclerViewHeadline.adapter = itemHeadlineAdapter
            } else {
                Toast.makeText(this, "Tidak ada berita", Toast.LENGTH_SHORT).show()
            }
        }

        btnRecent.setOnClickListener {
            setActiveNewsButton(btnRecent)
            btnRecent.setBackgroundResource(R.drawable.button_background)
            newsViewModel.fetchNews("tesla", todayDate, null, "pusblishedAt")
            newsViewModel.newList.observe(this) { articles ->
                newsAdapter.updateData(articles)
                if (articles.isNotEmpty()) {
                    newsAdapter = ItemNewsAdapter(articles) { article ->

                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("ARTICLE_URL", article)
                        startActivity(intent)
                    }
                    recyclerView.adapter = newsAdapter
                } else {
                    Toast.makeText(this, "Tidak ada berita", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnPopularity.setOnClickListener {
            setActiveNewsButton(btnPopularity)
            btnPopularity.setBackgroundResource(R.drawable.button_background)
            newsViewModel.fetchNews("apple", yesterdayDate, yesterdayDate, "popularity")
            newsViewModel.newList.observe(this) { articles ->
                newsAdapter.updateData(articles)
                if (articles.isNotEmpty()) {
                    newsAdapter = ItemNewsAdapter(articles) { article ->

                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("ARTICLE_URL", article)
                        startActivity(intent)
                    }
                    recyclerView.adapter = newsAdapter
                } else {
                    Toast.makeText(this, "Tidak ada berita", Toast.LENGTH_SHORT).show()
                }
            }

        }

        btnTechcrunch.setOnClickListener {
            setActiveHeadlineButton(btnTechcrunch)
            btnTechcrunch.setBackgroundResource(R.drawable.button_background)
            newsViewModel.fetchHeadlinesTechCrunch("techcrunch")
            newsViewModel.headlineList.observe(this) { articles ->
                itemHeadlineAdapter.updateData(articles)
                if (articles.isNotEmpty()) {
                    itemHeadlineAdapter = ItemHeadlineAdapter(articles) { article ->

                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("ARTICLE_URL", article)
                        startActivity(intent)
                    }
                    recyclerViewHeadline.adapter = itemHeadlineAdapter
                } else {
                    Toast.makeText(this, "Tidak ada berita", Toast.LENGTH_SHORT).show()
                }
            }

        }
        btnUS.setOnClickListener {
            setActiveHeadlineButton(btnUS)
            btnUS.setBackgroundResource(R.drawable.button_background)
            newsViewModel.fetchHeadlinesUSNow("us", "business")
            newsViewModel.headlineList.observe(this) { articles ->
                itemHeadlineAdapter.updateData(articles)
                if (articles.isNotEmpty()) {
                    itemHeadlineAdapter = ItemHeadlineAdapter(articles) { article ->

                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra("ARTICLE_URL", article)
                        startActivity(intent)
                    }
                    recyclerViewHeadline.adapter = itemHeadlineAdapter
                } else {
                    Toast.makeText(this, "Tidak ada berita", Toast.LENGTH_SHORT).show()
                }
            }

        }
        newsViewModel.error.observe(this) { errorMsg ->
            Log.e("APIError", "Terjadi kesalahan: $errorMsg")
            Toast.makeText(this, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setupButton(button: Button) {
        button.setOnClickListener {
            setActiveNewsButton(button)
        }
        button.setOnClickListener {
            setActiveHeadlineButton(button)
        }
    }


    private fun setActiveNewsButton(button: Button) {
        activeNewsButton?.apply {
            isSelected = false
            setTextColor(resources.getColor(R.color.white))
        }

        button.apply {
            isSelected = true
            setTextColor(resources.getColor(android.R.color.black))
        }
        activeNewsButton = button
    }

    private fun setActiveHeadlineButton(button: Button) {
        activeHeadlineButton?.apply {
            isSelected = false
            setTextColor(resources.getColor(R.color.white))
        }

        button.apply {
            isSelected = true
            setTextColor(resources.getColor(android.R.color.black))
        }
        activeHeadlineButton = button
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save->{
                val intent = Intent(this, SaveNewsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
