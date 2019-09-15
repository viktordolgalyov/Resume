package com.dolgalyov.resume.data.position.source

import com.dolgalyov.resume.common.arch.data.LocalDataSource
import com.dolgalyov.resume.data.position.model.PositionDto

typealias PositionLocalSource = LocalDataSource<String, PositionDto>