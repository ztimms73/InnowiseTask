package org.xtimms.innowise.weather.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import org.xtimms.innowise.weather.BuildConfig
import org.xtimms.innowise.weather.utils.SingleLiveEvent
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel : ViewModel() {

    val onError = SingleLiveEvent<Throwable>()
    val isLoading = MutableLiveData(false)

    protected fun launchJob(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + createErrorHandler(), start, block)

    protected fun launchLoadingJob(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + createErrorHandler(), start) {
        isLoading.postValue(true)
        try {
            block()
        } finally {
            isLoading.postValue(false)
        }
    }

    private fun createErrorHandler() = CoroutineExceptionHandler { _, throwable ->
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
        if (throwable !is CancellationException) {
            onError.postCall(throwable)
        }
    }
}