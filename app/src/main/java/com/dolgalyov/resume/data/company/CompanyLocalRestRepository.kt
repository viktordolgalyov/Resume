package com.dolgalyov.resume.data.company

import com.dolgalyov.resume.data.company.source.CompanyLocalSource
import com.dolgalyov.resume.data.company.source.CompanyRemoteSource

class CompanyLocalRestRepository(
    localSource: CompanyLocalSource,
    remoteSource: CompanyRemoteSource
) : CompanyRepository(localSource, remoteSource)