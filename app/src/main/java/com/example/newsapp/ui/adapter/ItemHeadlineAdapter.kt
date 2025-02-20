package com.example.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.response.Article


class ItemHeadlineAdapter(private var articles: List<Article>, private val onItemClick: (Article) -> Unit) :
    RecyclerView.Adapter<ItemHeadlineAdapter.ItemHeadlineViewHolder>() {

    class ItemHeadlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val author: TextView = itemView.findViewById(R.id.tvAuthor)
        val image: ImageView = itemView.findViewById(R.id.ivThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHeadlineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_headlines, parent, false)
        return ItemHeadlineViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHeadlineViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.author.text = article.author


        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.no_poto)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick(article)
        }
    }

    override fun getItemCount(): Int = articles.size
    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}