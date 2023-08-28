package com.verome.core.ui.di

import android.content.Context
import com.verome.core.ui.activity.ActivityProvider
import com.verome.core.ui.event.EventBus
import com.verome.core.ui.external.app.service.DefaultExternalAppService
import com.verome.core.ui.external.app.service.ExternalAppService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideExternalAppService(
        @ApplicationContext context: Context,
        activityProvider: ActivityProvider,
    ): ExternalAppService =
        DefaultExternalAppService(
            context = context,
            activityProvider = activityProvider,
        )
}