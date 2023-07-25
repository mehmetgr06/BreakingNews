package com.example.breakingnews.data

import com.example.breakingnews.data.model.NewsResponseModel
import com.example.breakingnews.data.model.SourcesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRemoteDataSource {

    @GET(ENDPOINT_SOURCES)
    suspend fun getSources(
        @Query("language") language: String = LANGUAGE_EN,
        @Query("category") category: String = "",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<SourcesResponseModel>

    @GET(ENDPOINT_NEWS)
    suspend fun getNews(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponseModel>

    companion object {
        const val ENDPOINT_SOURCES = "top-headlines/sources"
        const val ENDPOINT_NEWS = "top-headlines"
        const val API_KEY = "3cc266555b4a4b4f98a06810554b3c03"
        const val LANGUAGE_EN = "en"
    }
}
