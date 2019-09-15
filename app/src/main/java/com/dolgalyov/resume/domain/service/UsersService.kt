package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.data.user.model.UserDto
import com.dolgalyov.resume.domain.model.Email
import com.dolgalyov.resume.domain.model.User
import io.reactivex.Single

class UsersService(private val repository: UserRepository) {

    fun getUser(id: String): Single<User> {
        return repository.getById(id)
            .map { convertUser(it) }
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