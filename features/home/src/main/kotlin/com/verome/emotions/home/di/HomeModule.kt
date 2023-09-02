package com.verome.emotions.home.di

import com.verome.core.data.local.UserDatabase
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.emotions.home.data.repository.DefaultUserRepository
import com.verome.emotions.home.data.repository.DefaultEmotionsRepository
import com.verome.emotions.home.domain.repository.UserRepository
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
    fun provideCategoryRepository(
        db: UserDatabase,
        preferenceManager: PreferenceManager,
    ): EmotionsRepository =
        DefaultEmotionsRepository(
            dao = db.userDao,
            dispatcher = Dispatchers.IO,
            preferenceManager = preferenceManager,
        )

    @Provides
    @Singleton
    fun provideChangeUserDataRepository(
        db: UserDatabase,
        preferenceManager: PreferenceManager,
    ): UserRepository =
        DefaultUserRepository(
            dao = db.userDao,
            dispatcher = Dispatchers.IO,
            preferenceManager = preferenceManager,
        )
}