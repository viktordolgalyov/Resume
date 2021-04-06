package com.dolgalyov.resume.data.university.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.data.university.api.RawUniversity
import com.dolgalyov.resume.data.university.api.UniversityApi
import com.dolgalyov.resume.data.university.model.UniversityDto

class UniversityRemoteSource(
    private val api: UniversityApi
) : RemoteDataSource<String, UniversityDto> {

    override suspend fun getById(id: String): UniversityDto {
        val universities = api.getUniversities()
        val university = universities.firstOrNull { it.id == id }
        return university?.run(::convertToDto) ?: throw ItemNotFoundException(id)
    }

    private fun convertToDto(raw: RawUniversity) = UniversityDto(
        id = raw.id,
        name = raw.name,
        address = Address(
            country = raw.address.country.orEmpty(),
            city = raw.address.city.orEmpty()
        )
    )
}