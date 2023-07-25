package com.example.breakingnews.ui.model

data class SourcesState(
    var isLoading: Boolean = false,
    var sourceItems: Source? = null,
    var errorMessage: String = ""
)
