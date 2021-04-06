package com.dolgalyov.resume.common.arch.data

abstract class AbstractRepository<Id, Type>(
    private val localSource: LocalDataSource<Id, Type>,
    private val remoteSource: RemoteDataSource<Id, Type>
) {

    suspend fun getById(id: Id): Type {
        return localSource.getItem(id) ?: remoteSource.getById(id)
            .also { localSource.putItem(id, it) }
    }
}