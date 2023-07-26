package com.example.breakingnews.di

import com.example.breakingnews.data.datasource.NewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object NewsModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNewsRemoteDataSource(retrofit: Retrofit): NewsRemoteDataSource =
        retrofit.create(NewsRemoteDataSource::class.java)
}
