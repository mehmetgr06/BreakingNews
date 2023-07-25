package com.example.breakingnews.data

import com.example.breakingnews.data.model.NewsResponseModel
import com.example.breakingnews.data.model.SourcesResponseModel
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource
) {
    suspend fun getSources(category: String = ""): Response<SourcesResponseModel> =
        newsRemoteDataSource.getSources(category = category)

    suspend fun getNews(source: String): Response<NewsResponseModel> =
        newsRemoteDataSource.getNews(source)
}
