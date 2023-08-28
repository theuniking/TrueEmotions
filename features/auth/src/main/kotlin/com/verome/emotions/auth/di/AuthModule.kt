package com.verome.emotions.auth.di

import com.verome.core.data.local.UserDatabase
import com.verome.core.data.local.preferences.PreferenceManager
import com.verome.emotions.auth.data.repository.DefaultLoginRepository
import com.verome.emotions.auth.data.repository.DefaultRegistrationRepository
import com.verome.emotions.auth.domain.repository.LoginRepository
import com.verome.emotions.auth.domain.repository.RegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideRegistrationRepository(
        db: UserDatabase,
        preferenceManager: PreferenceManager,
    ): RegistrationRepository =
        DefaultRegistrationRepository(
            dao = db.userDao,
            preferenceManager = preferenceManager,
            dispatcher = Dispatchers.IO,
        )

    @Provides
    @Singleton
    fun provideLoginRepository(
        db: UserDatabase,
        preferenceManager: PreferenceManager,
    ): LoginRepository =
        DefaultLoginRepository(
            dao = db.userDao,
            preferenceManager = preferenceManager,
            dispatcher = Dispatchers.IO,
        )
}