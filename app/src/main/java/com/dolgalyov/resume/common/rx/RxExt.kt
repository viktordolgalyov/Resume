package com.dolgalyov.resume.common.rx

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun <T> T.toSingle(): Single<T> = Single.fromCallable { this }

fun <T> T.toFlowable(): Flowable<T> = Flowable.fromCallable { this }

fun <T> T.toMaybe(): Maybe<T> = Maybe.fromCallable { this }

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}