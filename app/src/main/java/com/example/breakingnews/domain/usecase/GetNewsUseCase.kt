package com.example.breakingnews.domain.usecase

import com.example.breakingnews.common.Result
import com.example.breakingnews.data.repository.NewsRepository
import com.example.breakingnews.data.model.ErrorResponse
import com.example.breakingnews.di.DispatcherModule
import com.example.breakingnews.domain.mapper.NewsMapper
import com.example.breakingnews.ui.model.News
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(source: String): Flow<Result<News?>> = flow {
        emit(Result.Loading())
        try {
            val response = newsRepository.getNews(source)
            if (response.isSuccessful) {
                emit(Result.Success(NewsMapper.mapToUiSource(response.body())))
            } else {
                val errorResponse = response.errorBody()?.string()
                val errorMessage = try {
                    // Extract the "message" field from the error response
                    val json = JSONObject(errorResponse.orEmpty())
                    ErrorResponse(json.getString("message"))
                } catch (e: JSONException) {
                    ErrorResponse("Unknown error occurred")
                }
                emit(Result.Error(errorMessage = errorMessage.message))
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
