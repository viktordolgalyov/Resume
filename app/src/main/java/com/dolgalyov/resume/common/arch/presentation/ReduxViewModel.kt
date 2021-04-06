package com.dolgalyov.resume.common.arch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dolgalyov.resume.common.arch.SingleLiveEvent
import com.dolgalyov.resume.common.util.Workers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "REDUX"

abstract class ReduxViewModel<A : UIAction, C : UIStateChange, S : UIState, M : UIModel>(
    protected val workers: Workers,
    private val reducer: Reducer<S, C>,
    private val stateToModelMapper: StateToModelMapper<S, M>
) : ViewModel() {
    private val modelName = javaClass.simpleName

    private val changes = Channel<C>(Channel.RENDEZVOUS)
    private val actions = Channel<A>(Channel.RENDEZVOUS)
    protected abstract var state: S
    protected abstract val errorHandler: ErrorHandler
    private val model = object : MutableLiveData<M>() {
        private var hasObserverBeenAttached = false

        override fun onActive() {
            val attachedFirstTime = !hasObserverBeenAttached
            if (!hasObserverBeenAttached) {
                Timber.tag(TAG).d("$modelName: observer attached first time")
                hasObserverBeenAttached = true
                bindChanges()
            } else {
                Timber.tag(TAG).d("$modelName: observer attached")
            }
            onObserverActive(attachedFirstTime)
        }

        override fun onInactive() {
            Timber.tag(TAG).d("$modelName: observer detached")
            onObserverInactive()
        }
    }
    private val event = object : SingleLiveEvent<UIEvent>() {

        override fun setValue(t: UIEvent?) {
            t?.let {
                if (t.isLoggable()) {
                    Timber.tag(TAG).d("$modelName: event created: ${t.log()}")
                }
            }

            super.setValue(t)
        }
    }

    /**
     * Returns the current presentation model
     */
    val observableModel: LiveData<M> = model

    /**
     * Emits events (only once): Toasts, Errors, Dialogs, etc.
     */
    val observableEvent: LiveData<UIEvent> = event

    init {
        Timber.tag(TAG).d("$modelName: viewModel created")
        viewModelScope.launch(workers.subscribeWorker) {
            actions.consumeEach {
                viewModelScope.launch {
                    val result = kotlin.runCatching { processAction(it) }
                    result.onFailure(::onError)
                }
            }
        }
    }

    /**
     * Dispatches an action. This is the only way to trigger a state/model change.
     */
    fun dispatch(action: A) {
        Timber.tag(TAG).d("$modelName: Received action: ${action.log()}")
        viewModelScope.launch(workers.subscribeWorker) { actions.send(action) }
    }

    fun sendChange(change: C) {
        viewModelScope.launch(workers.subscribeWorker) { changes.send(change) }
    }

    /**
     * Provide Flow which emits [UIStateChange].
     * This change will be converted to new [UIState] with [reducer]
     * */
    protected abstract fun processAction(action: A)

    protected abstract suspend fun provideChangesObservable(): Flow<C>

    protected open fun onObserverActive(firstTime: Boolean) {}

    protected open fun onObserverInactive() {}

    protected fun onError(error: Throwable) {
        Timber.e(error)
        viewModelScope.launch(workers.observeWorker) { errorHandler(error) }
    }

    override fun onCleared() {
        Timber.tag(TAG).d("$modelName: viewModel destroyed")
    }

    private fun bindChanges() {
        viewModelScope.launch(workers.subscribeWorker) {
            merge(
                provideChangesObservable().catch { e -> onError(e) },
                changes.receiveAsFlow().catch { e -> onError(e) }
            )
                .distinctUntilChanged()
                .flatMapConcat { change ->
                    if (change.isLoggable()) {
                        Timber.tag(modelName).d("change received: ${change.log()}")
                    }
                    flow {
                        emit(reducer.reduce(state, change).also { this@ReduxViewModel.state = it })
                    }
                }
                .distinctUntilChanged()
                .onStart { emit(state) }
                .map { stateToModelMapper.mapStateToModel(it) }
                .distinctUntilChanged()
                .catch { e -> onError(e) }
                .collectLatest {
                    if (it.isLoggable()) {
                        Timber.tag(modelName).i("Model updated: ${it.log()}")
                    }
                    viewModelScope.launch { model.value = it }
                }
        }
    }

    protected suspend inline fun <T> execute(
        crossinline action: suspend () -> T,
        crossinline onStart: () -> Unit = {},
        crossinline onSuccess: (T) -> Unit,
        crossinline onComplete: () -> Unit = {},
        noinline onErrorOccurred: ((Throwable) -> Unit)? = null
    ) {
        onStart()
        try {
            val result = action()
            onSuccess(result)
        } catch (e: Exception) {
            onErrorOccurred?.invoke(e) ?: onError(e)
        }
        onComplete()
    }
}