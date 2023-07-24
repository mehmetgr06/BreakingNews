package com.example.breakingnews.data

import com.example.breakingnews.base.Result
import com.example.breakingnews.data.model.SourcesResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource
) {
    fun getSources(): Flow<Result<SourcesResponseModel?>> = flow {
        newsRemoteDataSource.getSources()
    }
}
