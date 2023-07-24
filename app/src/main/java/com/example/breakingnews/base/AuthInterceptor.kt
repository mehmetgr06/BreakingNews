package com.example.breakingnews.base

import com.example.breakingnews.base.AppConstants.API_KEY
import com.example.breakingnews.base.AppConstants.HEADER_API_KEY
import com.example.breakingnews.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/***
 *  No need for this class because NewsApi expects apiKey as Query param.
 *  This is a showcase how Auth keys must be added to header in actual request.
 */
class AuthInterceptor @Inject constructor(
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        runBlocking(ioDispatcher) {
            builder.header(HEADER_API_KEY, API_KEY)
        }
        return chain.proceed(builder.build())
    }
}
