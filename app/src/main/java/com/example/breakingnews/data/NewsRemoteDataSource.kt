package com.example.breakingnews.data

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

    companion object {
        const val ENDPOINT_SOURCES = "top-headlines/sources"
        const val API_KEY = "1ccfa43459824f8492906fd1b3c4b04e"
        const val LANGUAGE_EN = "en"
    }
}
