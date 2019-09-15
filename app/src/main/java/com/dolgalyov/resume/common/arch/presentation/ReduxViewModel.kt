package com.dolgalyov.resume.common.arch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolgalyov.resume.common.arch.SingleLiveEvent
import com.dolgalyov.resume.common.rx.RxWorkers
import com.dolgalyov.resume.common.rx.composeWith
import com.dolgalyov.resume.common.rx.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private const val TAG = "REDUX"
private const val REDUCER_THREAD_NAME = "ReduxReducerThread"

abstract class ReduxViewModel<A : UIAction, C : UIStateChange, S : UIState, M : UIModel>(
    protected val workers: RxWorkers,
    private val reducer: Reducer<S, C>,
    private val stateToModelMapper: StateToModelMapper<S, M>
) : ViewModel() {
    private val modelName = javaClass.simpleName

    protected val actions = PublishSubject.create<A>()
    protected val disposables = CompositeDisposable()
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

    protected val event = object : SingleLiveEvent<UIEvent>() {

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
    }

    /**
     * Dispatches an action. This is the only way to trigger a state/model change.
     */
    fun dispatch(action: A) {
        Timber.tag(TAG).d("$modelName: Received action: ${action.log()}")
        actions.onNext(action)
    }

    /**
     * Provide Observable which emits [UIStateChange].
     * This change will be converted to new [UIState] with [reducer]
     * */
    protected abstract fun provideChangesObservable(): Observable<C>

    protected open fun onObserverActive(firstTime: Boolean) {}

    protected open fun onObserverInactive() {}

    private fun onError(error: Throwable) {
        Timber.e(error)
        workers.observeWorker.scheduleDirect { errorHandler(error) }
    }

    override fun onCleared() {
        Timber.tag(TAG).d("$modelName: viewModel destroyed")
        disposables.clear()
    }

    private fun bindChanges() {
        val reducerScheduler = Schedulers.from(
            Executors.newSingleThreadExecutor { r -> Thread(r,
                REDUCER_THREAD_NAME
            ) })

        disposables += provideChangesObservable()
            .observeOn(reducerScheduler)
            .map { change ->
                reducer.reduce(state, change)
                    .also { this.state = it }
            }
            .startWith(state)
            .distinctUntilChanged()
            .map(stateToModelMapper::mapStateToModel)
            .distinctUntilChanged()
            .throttleLast(50, TimeUnit.MILLISECONDS)
            .doOnNext { Timber.tag(TAG).d("$modelName: model updated: ${it.log()}") }
            .composeWith(workers)
            .doOnTerminate { Timber.tag(TAG).e("Changes observable terminated!") }
            .subscribe(model::setValue, ::onError)
    }
}