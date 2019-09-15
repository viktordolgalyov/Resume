package com.dolgalyov.resume.data.user

import com.dolgalyov.resume.data.user.source.UserLocalSource
import com.dolgalyov.resume.data.user.source.UserRemoteSource

class UserLocalRestRepository(
    localSource: UserLocalSource,
    remoteSource: UserRemoteSource
) : UserRepository(localSource, remoteSource)