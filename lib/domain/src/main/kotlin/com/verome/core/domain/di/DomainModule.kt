package com.verome.core.domain.di

import com.verome.core.domain.error.handling.ErrorHandler
import com.verome.core.domain.message.MessageHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideMessageHandler(): MessageHandler = MessageHandler()

    @Provides
    fun provideErrorHandler(messageHandler: MessageHandler): ErrorHandler = ErrorHandler(messageHandler)
}