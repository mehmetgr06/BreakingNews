package com.example.breakingnews.ui.model

data class NewsState(
    var isLoading: Boolean = false,
    var newstems: News? = null,
    var errorMessage: String = ""
)
