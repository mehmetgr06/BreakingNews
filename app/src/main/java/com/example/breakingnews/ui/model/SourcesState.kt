package com.example.breakingnews.ui.model

data class SourcesState(
    var isLoading: Boolean = false,
    var sourceItems: List<SourceItem>? = null,
    var errorMessage: String = ""
)
