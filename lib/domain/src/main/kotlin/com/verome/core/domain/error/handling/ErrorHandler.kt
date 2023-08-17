package com.verome.core.domain.error.handling

import com.verome.core.domain.localization.string.VmRes
import com.verome.core.domain.message.Message
import com.verome.core.domain.message.MessageHandler
import com.verome.domain.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val messageHandler: MessageHandler,
) {

    private val unauthorizedEventChannel = Channel<Unit>(Channel.UNLIMITED)

    val unauthorizedEventFlow = unauthorizedEventChannel.receiveAsFlow()

    fun handleError(throwable: Throwable, showError: Boolean = true) {
        Timber.e(throwable)
        when {
            throwable is UnauthorizedException && throwable.status == UNAUTHORIZED -> {
                unauthorizedEventChannel.trySend(Unit)
            }

            showError -> {
                messageHandler.showMessage(
                    Message(
                        title = VmRes.StrRes(R.string.error_title),
                        description = throwable.errorMessage,
                        positiveButtonText = VmRes.StrRes(R.string.common_close),
                    ),
                )
            }
        }
    }
}