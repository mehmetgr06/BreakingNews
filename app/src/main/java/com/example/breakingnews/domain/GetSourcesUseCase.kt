package com.example.breakingnews.domain

import com.example.breakingnews.data.NewsRepository
import com.example.breakingnews.data.model.SourcesResponseModel
import com.example.breakingnews.di.DispatcherModule
import com.example.breakingnews.base.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<Result<SourcesResponseModel?>> =
        flow {
            emit(Result.Loading())
            try {
                val response = newsRepository.getSources()
                response.collect { result ->
                    emit(Result.Success(result.data))
                }
            } catch (e: HttpException) {
                emit(
                    Result.Error(
                        errorMessage = e.localizedMessage ?: "A network error has occurred"
                    )
                )
            }
        }.flowOn(ioDispatcher)
}
