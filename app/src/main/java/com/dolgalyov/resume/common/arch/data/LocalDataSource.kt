package com.dolgalyov.resume.common.arch.data

import com.dolgalyov.resume.common.rx.toMaybe
import io.reactivex.Completable
import io.reactivex.Maybe
import java.util.concurrent.ConcurrentHashMap

class LocalDataSource<Key, Type> {
    private val cache = ConcurrentHashMap<Key, Type>()

    fun getItem(id: Key): Maybe<Type> {
        return cache[id]?.run { toMaybe() } ?: Maybe.empty()
    }

    fun putItem(key: Key, item: Type): Completable {
        return Completable.fromAction { cache[key] = item }
    }
}