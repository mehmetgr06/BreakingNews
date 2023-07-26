package com.example.breakingnews.domain.usecase

import com.example.breakingnews.data.repository.NewsRepository
import com.example.breakingnews.ui.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    operator fun invoke(): Flow<List<Article>> = newsRepository.getSavedArticles()
}
