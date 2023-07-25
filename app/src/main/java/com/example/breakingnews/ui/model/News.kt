package com.example.breakingnews.ui.model

import com.example.breakingnews.data.model.NewsSource

data class News(
    val articles: List<Article?>?,
    val status: String?,
    val totalResults: Int?
)

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: NewsSource?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

data class NewsSource(
    val id: String?,
    val name: String?
)