package com.example.breakingnews.domain

import com.example.breakingnews.data.NewsRepository
import com.example.breakingnews.ui.model.Source
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(): List<Source> {
        val responseModel = newsRepository.getSources().single()
        return SourcesMapper.mapToUiSource(responseModel.data)
    }
}
