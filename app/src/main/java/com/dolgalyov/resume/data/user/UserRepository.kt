package com.dolgalyov.resume.data.user

import com.dolgalyov.resume.common.arch.data.AbstractRepository
import com.dolgalyov.resume.data.user.model.UserDto

typealias UserRepository = AbstractRepository<String, UserDto>