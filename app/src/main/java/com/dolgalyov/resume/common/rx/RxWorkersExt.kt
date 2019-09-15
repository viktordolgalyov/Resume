package com.dolgalyov.resume.common.rx

import io.reactivex.*

fun <T> Single<T>.composeWith(workers: RxWorkers) = compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}

fun Completable.composeWith(workers: RxWorkers) = compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}

fun <T> Observable<T>.composeWith(workers: RxWorkers) = compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}

fun <T> Maybe<T>.composeWith(workers: RxWorkers) = compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}

fun <T> Flowable<T>.composeWith(workers: RxWorkers) = compose {
    it.subscribeOn(workers.subscribeWorker).observeOn(workers.observeWorker)
}