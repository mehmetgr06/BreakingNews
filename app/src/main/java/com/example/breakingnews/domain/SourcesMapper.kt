package com.example.breakingnews.domain

import com.example.breakingnews.data.model.SourcesResponseModel
import com.example.breakingnews.ui.model.Source

object SourcesMapper {
    fun mapToUiSource(responseModel: SourcesResponseModel?): List<Source> {
        return responseModel?.sources?.map { source ->
            Source(
                category = source.category,
                country = source.country,
                description = source.description,
                id = source.id,
                language = source.language,
                name = source.name,
                url = source.url
            )
        } ?: emptyList()
    }
}
