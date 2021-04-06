package com.dolgalyov.resume.common.util

import kotlinx.coroutines.CoroutineDispatcher

data class Workers(
    val subscribeWorker: CoroutineDispatcher,
    val observeWorker: CoroutineDispatcher
)