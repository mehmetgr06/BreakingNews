package com.example.breakingnews.domain

import com.example.breakingnews.data.NewsRepository
import com.example.breakingnews.ui.model.Article
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) = newsRepository.saveArticle(article)
}
