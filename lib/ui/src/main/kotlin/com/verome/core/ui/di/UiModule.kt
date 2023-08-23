package com.verome.core.ui.di

import com.verome.core.ui.activity.ActivityProvider
import com.verome.core.ui.event.EventBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    @Singleton
    fun provideActivityProvider(): ActivityProvider = ActivityProvider()

    @Provides
    @Singleton
    fun provideEventBus(): EventBus = EventBus()
}