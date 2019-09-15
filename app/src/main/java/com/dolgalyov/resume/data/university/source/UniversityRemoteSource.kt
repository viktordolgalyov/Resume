package com.dolgalyov.resume.data.university.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.data.university.api.RawUniversity
import com.dolgalyov.resume.data.university.api.UniversityApi
import com.dolgalyov.resume.data.university.model.UniversityDto
import io.reactivex.Single

class UniversityRemoteSource(private val api: UniversityApi) :
    RemoteDataSource<String, UniversityDto> {

    override fun getById(id: String): Single<UniversityDto> {
        return api.getUniversities().map { response ->
            val raw = response.firstOrNull { it.id == id }
            raw?.run { convertToDto(this) } ?: throw ItemNotFoundException(
                id
            )
        }
    }

    private fun convertToDto(raw: RawUniversity) =
        UniversityDto(
            id = raw.id,
            name = raw.name,
            address = Address(
                country = raw.address.country.orEmpty(),
                city = raw.address.city.orEmpty()
            )
        )
}