package com.verome.emotions.home.di

import com.verome.core.data.local.UserDatabase
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.emotions.home.data.repository.DefaultChangeUserDataRepository
import com.verome.emotions.home.data.repository.DefaultEmotionsRepository
import com.verome.emotions.home.domain.repository.ChangeUserDataRepository
import com.verome.emotions.home.domain.repository.EmotionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideCategoryRepository(): EmotionsRepository = DefaultEmotionsRepository()

    @Provides
    @Singleton
    fun provideChangeUserDataRepository(
        db: UserDatabase,
        preferenceManager: PreferenceManager,
    ): ChangeUserDataRepository =
        DefaultChangeUserDataRepository(
            dao = db.userDao,
            dispatcher = Dispatchers.IO,
            preferenceManager = preferenceManager,
        )
}