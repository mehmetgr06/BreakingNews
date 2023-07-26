package com.example.breakingnews.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    val articles: List<Article?>?,
    val status: String?,
    val totalResults: Int?
)

@Entity
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @PrimaryKey val title: String = "",
    val url: String?,
    val urlToImage: String?
)
