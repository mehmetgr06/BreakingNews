package com.example.breakingnews.domain

import com.example.breakingnews.data.model.SourcesResponseModel
import com.example.breakingnews.ui.model.Source
import com.example.breakingnews.ui.model.SourceItem

object SourcesMapper {
    fun mapToUiSource(responseModel: SourcesResponseModel?): Source? {
        return responseModel?.let { response ->
            Source(
                response.sources?.map { source ->
                    SourceItem(
                        category = source.category,
                        country = source.country,
                        description = source.description,
                        id = source.id,
                        language = source.language,
                        name = source.name,
                        url = source.url
                    )
                },
                response.status,
                response.sources?.map {
                    it.category.toString()
                }?.distinct()
            )
        }
    }
}
