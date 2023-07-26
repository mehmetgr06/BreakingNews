package com.example.breakingnews.domain

import com.example.breakingnews.data.model.NewsResponseModel
import com.example.breakingnews.ui.model.Article
import com.example.breakingnews.ui.model.News

object NewsMapper {
    fun mapToUiSource(responseModel: NewsResponseModel?): News? {
        return responseModel?.let { response ->
            News(
                response.articles?.map { article ->
                    Article(
                        author = article.author,
                        content = article.content,
                        description = article.description,
                        publishedAt = article.publishedAt,
                        title = article.title.orEmpty(),
                        url = article.url,
                        urlToImage = article.urlToImage
                    )
                },
                response.status,
                response.totalResults
            )
        }
    }
}
