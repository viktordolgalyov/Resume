package com.dolgalyov.resume.data.position

import com.dolgalyov.resume.data.position.source.PositionLocalSource
import com.dolgalyov.resume.data.position.source.PositionRemoteSource

class PositionLocalRestRepository(
    localSource: PositionLocalSource,
    remoteSource: PositionRemoteSource
) : PositionRepository(localSource, remoteSource)