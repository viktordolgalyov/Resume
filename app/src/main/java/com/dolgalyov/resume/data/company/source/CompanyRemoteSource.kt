package com.dolgalyov.resume.data.company.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.data.address.Address
import com.dolgalyov.resume.data.company.api.CompanyApi
import com.dolgalyov.resume.data.company.api.RawCompany
import com.dolgalyov.resume.data.company.model.CompanyDto

class CompanyRemoteSource(
    private val api: CompanyApi
) : RemoteDataSource<String, CompanyDto> {

    override suspend fun getById(id: String): CompanyDto {
        val companies = api.getCompanies()
        val company = companies.firstOrNull { it.id == id }
        return company?.run(::convertToDto) ?: throw ItemNotFoundException(id)
    }

    private fun convertToDto(rawCompany: RawCompany) = CompanyDto(
        id = rawCompany.id,
        name = rawCompany.name,
        address = Address(
            country = rawCompany.address.country.orEmpty(),
            city = rawCompany.address.city.orEmpty()
        )
    )
}