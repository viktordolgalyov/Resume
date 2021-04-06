package com.dolgalyov.resume.common.arch.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class LocalDataSource<Key, Type> {
    private val cache = ConcurrentHashMap<Key, Type>()

    suspend fun getItem(id: Key): Type? = withContext(Dispatchers.IO) {
        return@withContext cache[id]
    }

    suspend fun putItem(key: Key, item: Type) = withContext(Dispatchers.IO) {
        cache[key] = item
    }
}