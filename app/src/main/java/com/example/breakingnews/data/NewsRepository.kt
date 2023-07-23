package com.example.breakingnews.data

import com.example.breakingnews.base.Result
import com.example.breakingnews.data.model.SourcesRequestModel
import com.example.breakingnews.data.model.SourcesResponseModel
import com.example.breakingnews.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class NewsRepository(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val newsRemoteDataSource: NewsRemoteDataSource,
) {

    fun getSources(request: SourcesRequestModel = SourcesRequestModel()): Flow<Result<SourcesResponseModel?>> =
        flow {
            try {
                emit(Result.Loading())
                val response = newsRemoteDataSource.getSources(request)
                emit(Result.Success(response.body()))
            } catch (e: HttpException) {
                emit(
                    Result.Error(
                        errorMessage = e.localizedMessage ?: "A network error has occured"
                    )
                )
            }
        }.flowOn(ioDispatcher)
}
