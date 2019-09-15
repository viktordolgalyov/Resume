package com.dolgalyov.resume.common.log

import timber.log.Timber

class TimberRemoteTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        // send logs to the server(crashlytics, kibana etc)
    }
}