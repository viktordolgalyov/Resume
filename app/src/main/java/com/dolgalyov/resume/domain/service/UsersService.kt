package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.data.user.model.UserDto
import com.dolgalyov.resume.domain.model.Email
import com.dolgalyov.resume.domain.model.User

class UsersService(private val repository: UserRepository) {

    suspend fun getUser(id: String): User {
        val user = repository.getById(id)
        return convertUser(user)
    }

    private fun convertUser(dto: UserDto) = User(
        id = dto.id,
        name = dto.name,
        photo = dto.photo,
        about = dto.about,
        address = dto.address,
        email = Email(dto.email)
    )
}