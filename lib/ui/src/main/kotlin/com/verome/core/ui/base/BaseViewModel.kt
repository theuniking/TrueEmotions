package com.verome.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verome.core.domain.error.handling.ErrorHandler
import com.verome.core.ui.event.EventBus
import com.verome.core.ui.event.SystemEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {

    @Inject lateinit var errorHandler: ErrorHandler

    @Inject lateinit var eventBus: EventBus

    fun sendEvent(event: SystemEvent) {
        eventBus.sendEvent(event)
    }

    fun sendEvents(events: List<SystemEvent>) {
        eventBus.sendEvents(events)
    }

    /**
     * Данный метод использовается для безопасного вызова корутины.
     * В методе можно выбрать dispatcher,
     * в request указывается, набор функций, которые нужно сделать в отдельном потоке
     *
     * в onSuccess указывается, набор функций, которые трубуется вызвать
     * при успешном выполнении функций в request
     *
     * в onError указывается, набор функций, которые трубуется вызвать
     * при неуспешном выполнении функций в request
     *
     * в finally указывается, набор функций по завершению работу
     *
     * shouldUseBasicErrorHandler - указывает требуется ли стандартный
     * перехват ошибок через ErrorHandler
     */
    fun <T> dataRequest(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        loading: ((Boolean) -> Unit)? = null,
        request: suspend () -> T,
        onSuccess: ((T) -> Unit)? = null,
        shouldUseBasicErrorHandler: Boolean = true,
        onError: ((Throwable) -> Unit)? = null,
        finally: (() -> Unit)? = null,
    ) = viewModelScope.launch(dispatcher) {
        try {
            loading?.invoke(true)
            val response = request()
            loading?.invoke(false)
            onSuccess?.invoke(response)
        } catch (e: CancellationException) {
            loading?.invoke(false)
        } catch (e: Throwable) {
            loading?.invoke(false)
            onError?.invoke(e)
            if (shouldUseBasicErrorHandler) {
                errorHandler.handleError(e)
            }
        } finally {
            finally?.invoke()
        }
    }
}
