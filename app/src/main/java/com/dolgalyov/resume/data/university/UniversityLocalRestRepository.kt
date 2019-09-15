package com.dolgalyov.resume.data.university

import com.dolgalyov.resume.data.university.source.UniversityLocalSource
import com.dolgalyov.resume.data.university.source.UniversityRemoteSource

class UniversityLocalRestRepository(
    localSource: UniversityLocalSource,
    remoteSource: UniversityRemoteSource
) : UniversityRepository(localSource, remoteSource)