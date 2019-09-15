package com.dolgalyov.resume.data.user.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.data.user.api.RawUser
import com.dolgalyov.resume.data.user.api.UserApi
import com.dolgalyov.resume.data.user.model.UserDto
import io.reactivex.Single

class UserRemoteSource(private val api: UserApi) :
    RemoteDataSource<String, UserDto> {

    override fun getById(id: String): Single<UserDto> {
        return api.getUsers().map { response ->
            val raw = response.firstOrNull { it.id == id }
            raw?.run { convertToDto(this) } ?: throw ItemNotFoundException(
                id
            )
        }
    }

    private fun convertToDto(raw: RawUser) = UserDto(
        id = raw.id,
        name = raw.name,
        photo = raw.photo,
        about = raw.about,
        email = raw.email,
        address = Address(
            country = raw.address.country.orEmpty(),
            city = raw.address.city.orEmpty()
        ),
        positions = raw.positions,
        education = raw.education,
        skills = raw.skills
    )
}