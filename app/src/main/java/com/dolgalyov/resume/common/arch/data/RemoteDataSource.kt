package com.dolgalyov.resume.common.arch.data

import com.dolgalyov.resume.common.exception.ItemNotFoundException

interface RemoteDataSource<Id, Type> {

    @Throws(ItemNotFoundException::class)
    suspend fun getById(id: Id): Type
}