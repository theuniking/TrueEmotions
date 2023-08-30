package com.verome.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.verome.core.data.local.UserDatabase
import com.verome.core.data.local.preferences.DefaultPreferenceManager
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.core.data.repository.DefaultEmotionsCategoryRepository
import com.verome.core.domain.repository.EmotionsCategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideEmotionsCategoryRepository(): EmotionsCategoryRepository =
        DefaultEmotionsCategoryRepository()

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            UserDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun providePreferenceManager(
        @ApplicationContext context: Context,
    ): PreferenceManager = DefaultPreferenceManager(context = context)
}