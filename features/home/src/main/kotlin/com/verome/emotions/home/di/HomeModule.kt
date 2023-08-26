package com.verome.emotions.home.di

import com.verome.emotions.home.data.repository.DefaultEmotionsRepository
import com.verome.emotions.home.domain.repository.EmotionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideCategoryRepository(): EmotionsRepository = DefaultEmotionsRepository()
}