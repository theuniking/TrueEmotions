package com.verome.core.ui.di

import com.verome.core.ui.activity.ActivityProvider
import com.verome.core.ui.event.EventBus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun provideActivityProvider(): ActivityProvider = ActivityProvider()

    @Provides
    fun provideEventBus(): EventBus = EventBus()
}