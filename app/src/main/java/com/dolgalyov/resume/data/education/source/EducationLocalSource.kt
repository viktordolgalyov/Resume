package com.dolgalyov.resume.data.education.source

import com.dolgalyov.resume.common.arch.data.LocalDataSource
import com.dolgalyov.resume.data.education.model.EducationRecordDto

typealias EducationLocalSource = LocalDataSource<String, EducationRecordDto>