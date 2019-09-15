package com.dolgalyov.resume.common.arch.data

import com.dolgalyov.resume.common.exception.ItemNotFoundException
import io.reactivex.Single

interface RemoteDataSource<Id, Type> {

    @Throws(ItemNotFoundException::class)
    fun getById(id: Id): Single<Type>
}