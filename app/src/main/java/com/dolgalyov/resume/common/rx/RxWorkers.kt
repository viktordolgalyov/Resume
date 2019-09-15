package com.dolgalyov.resume.common.rx

import io.reactivex.Scheduler

data class RxWorkers(val subscribeWorker: Scheduler, val observeWorker: Scheduler)