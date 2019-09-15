package com.dolgalyov.resume.data.education

import com.dolgalyov.resume.data.education.source.EducationLocalSource
import com.dolgalyov.resume.data.education.source.EducationRemoteSource

class EducationLocalRestRepository(
    localSource: EducationLocalSource,
    remoteSource: EducationRemoteSource
) : EducationRepository(localSource, remoteSource)