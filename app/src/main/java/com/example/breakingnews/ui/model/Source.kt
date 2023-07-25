package com.example.breakingnews.ui.model

data class Source(
    val sources: List<SourceItem>?,
    val status: String?,
    val categoryList: List<String>?
)

data class SourceItem(
    val category: String?,
    val country: String?,
    val description: String?,
    val id: String?,
    val language: String?,
    val name: String?,
    val url: String?
)
