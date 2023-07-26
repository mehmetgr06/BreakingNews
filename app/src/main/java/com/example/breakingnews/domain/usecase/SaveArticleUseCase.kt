package com.example.breakingnews.domain.usecase

import com.example.breakingnews.data.repository.NewsRepository
import com.example.breakingnews.ui.model.Article
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) = newsRepository.saveArticle(article)
}
