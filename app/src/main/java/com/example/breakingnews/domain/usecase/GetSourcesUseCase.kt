package com.example.breakingnews.domain.usecase

import com.example.breakingnews.data.repository.NewsRepository
import com.example.breakingnews.di.DispatcherModule
import com.example.breakingnews.common.Result
import com.example.breakingnews.data.model.ErrorResponse
import com.example.breakingnews.domain.mapper.SourcesMapper
import com.example.breakingnews.ui.model.Source
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(category: String = ""): Flow<Result<Source?>> =
        flow {
            emit(Result.Loading())
            try {
                val response = newsRepository.getSources(category = category)
                if(response.isSuccessful) {
                    emit(Result.Success(SourcesMapper.mapToUiSource(response.body())))
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
