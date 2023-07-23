package com.example.breakingnews.data.model

import com.google.gson.annotations.SerializedName

data class SourcesRequestModel(
    @SerializedName("category") val category: String = "",
    @SerializedName("language") val language: String = "en",
    @SerializedName("country") val country: String = ""
)
