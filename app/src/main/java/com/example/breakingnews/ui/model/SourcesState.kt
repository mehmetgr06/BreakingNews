package com.example.breakingnews.ui.model

data class SourcesState(
    var isLoading: Boolean = false,
    var sources: List<Source>? = null,
    var errorMessage: String = ""
)
