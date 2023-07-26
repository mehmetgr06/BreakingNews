package com.example.breakingnews.data

import com.example.breakingnews.data.model.NewsResponseModel
import com.example.breakingnews.data.model.SourcesResponseModel
import com.example.breakingnews.ui.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsDao: NewsDao
) {
    suspend fun getSources(category: String = ""): Response<SourcesResponseModel> =
        newsRemoteDataSource.getSources(category = category)

    suspend fun getNews(source: String): Response<NewsResponseModel> =
        newsRemoteDataSource.getNews(source)

    fun getSavedArticles(): Flow<List<Article>> = newsDao.getSavedArticles()

    suspend fun saveArticle(article: Article) = newsDao.saveArticle(article)

    suspend fun unSaveArticle(article: Article) = newsDao.unSaveArticle(article)
}
