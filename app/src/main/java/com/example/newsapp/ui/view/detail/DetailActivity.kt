package com.example.newsapp.ui.view.detail

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.response.Article
import formatPublishedDate

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val article = intent.getSerializableExtra("ARTICLE_URL") as? Article
        val titleTextView = findViewById<TextView>(R.id.tvTitle)
        val authorTextView = findViewById<TextView>(R.id.tvAuthor)
        val descriptionTextView = findViewById<TextView>(R.id.tvDescription)
        val publishTextView = findViewById<TextView>(R.id.tvPublish)
        val imageView = findViewById<ImageView>(R.id.ivThumbnail)
        val btnSave = findViewById<Button>(R.id.btnIcon)


        btnSave.setOnClickListener {
            if (article != null) {

                Toast.makeText(this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal menyimpan! Data tidak ditemukan.", Toast.LENGTH_SHORT).show()
            }
        }

        titleTextView.text = article?.title
        authorTextView.text = article?.author
        descriptionTextView.text = article?.description
        publishTextView.text = formatPublishedDate(article?.publishedAt!!)
        Glide.with(this)
            .load(article?.urlToImage)
            .placeholder(R.drawable.no_poto)
            .error(R.drawable.no_poto)
            .into(imageView)

    }
}