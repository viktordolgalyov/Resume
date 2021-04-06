package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.company.CompanyRepository
import com.dolgalyov.resume.data.company.model.CompanyDto
import com.dolgalyov.resume.data.position.PositionRepository
import com.dolgalyov.resume.data.position.model.PositionDto
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.domain.model.Company
import com.dolgalyov.resume.domain.model.Position

class PositionService(
    private val usersRepository: UserRepository,
    private val companyRepository: CompanyRepository,
    private val positionRepository: PositionRepository
) {

    suspend fun getPosition(id: String): Position {
        val position = positionRepository.getById(id)
        val company = companyRepository.getById(position.companyId).run(::convertCompany)
        return convertPosition(company, position)
    }

    suspend fun getUserPositions(userId: String): List<Position> {
        return usersRepository.getById(userId).positions
            .map { positionId -> getPosition(positionId) }
    }

    private fun convertPosition(
        company: Company,
        positionDto: PositionDto
    ) = Position(
        id = positionDto.id,
        name = positionDto.name,
        company = company,
        description = positionDto.description,
        from = positionDto.from,
        to = positionDto.to
    )

    private fun convertCompany(companyDto: CompanyDto) = Company(
        id = companyDto.id,
        name = companyDto.name,
        address = companyDto.address
    )
}