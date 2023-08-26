package com.verome.core.di

import com.verome.core.data.repository.DefaultEmotionsCategoryRepository
import com.verome.core.domain.repository.EmotionsCategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideEmotionsCategoryRepository(): EmotionsCategoryRepository = DefaultEmotionsCategoryRepository()
}