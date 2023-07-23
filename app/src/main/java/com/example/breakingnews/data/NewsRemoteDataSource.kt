package com.example.breakingnews.data

import com.example.breakingnews.data.model.SourcesRequestModel
import com.example.breakingnews.data.model.SourcesResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface NewsRemoteDataSource {

    @GET(ENDPOINT_SOURCES)
    suspend fun getSources(@Body request: SourcesRequestModel): Response<SourcesResponseModel>

    companion object {
        const val ENDPOINT_SOURCES = "top-headlines/sources"
    }
}
