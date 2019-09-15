package com.dolgalyov.resume.data.user.source

import com.dolgalyov.resume.common.arch.data.LocalDataSource
import com.dolgalyov.resume.data.user.model.UserDto

typealias UserLocalSource = LocalDataSource<String, UserDto>