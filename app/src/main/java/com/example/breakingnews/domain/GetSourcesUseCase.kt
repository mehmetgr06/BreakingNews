package com.example.breakingnews.domain

import com.example.breakingnews.data.NewsRepository
import com.example.breakingnews.di.DispatcherModule
import com.example.breakingnews.base.Result
import com.example.breakingnews.ui.model.Source
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<Result<Source?>> =
        flow {
            emit(Result.Loading())
            //to show shimmer animation
            delay(1500)
            try {
                val response = newsRepository.getSources()
                emit(Result.Success(SourcesMapper.mapToUiSource(response.body())))
            } catch (e: HttpException) {
                emit(
                    Result.Error(
                        errorMessage = e.localizedMessage ?: "A network error has occurred"
                    )
                )
            }
        }.flowOn(ioDispatcher)
}
