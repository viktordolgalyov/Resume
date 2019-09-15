package com.dolgalyov.resume.common.arch.data

import com.dolgalyov.resume.common.rx.toSingle
import io.reactivex.Single

abstract class AbstractRepository<Id, Type>(
    private val localSource: LocalDataSource<Id, Type>,
    private val remoteSource: RemoteDataSource<Id, Type>
) {

    fun getById(id: Id): Single<Type> {
        return localSource.getItem(id)
            .switchIfEmpty(remoteSource.getById(id).flatMap {
                localSource.putItem(id, it).andThen(it.toSingle())
            })
    }
}