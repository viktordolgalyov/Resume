package com.dolgalyov.resume.domain.service

import com.dolgalyov.resume.data.company.CompanyRepository
import com.dolgalyov.resume.data.company.model.CompanyDto
import com.dolgalyov.resume.data.position.PositionRepository
import com.dolgalyov.resume.data.position.model.PositionDto
import com.dolgalyov.resume.data.user.UserRepository
import com.dolgalyov.resume.domain.model.Company
import com.dolgalyov.resume.domain.model.Position
import io.reactivex.Observable
import io.reactivex.Single

class PositionService(
    private val usersRepository: UserRepository,
    private val companyRepository: CompanyRepository,
    private val positionRepository: PositionRepository
) {

    fun getPosition(id: String): Single<Position> {
        return positionRepository.getById(id).flatMap { positionDto ->
            companyRepository.getById(positionDto.companyId).map { companyDto ->
                val company = convertCompany(companyDto)
                convertPosition(company, positionDto)
            }
        }
    }

    fun getUserPositions(userId: String): Single<List<Position>> {
        return usersRepository.getById(userId).flatMap { user ->
            Observable
                .fromIterable(user.positions)
                .flatMapSingle { positionId -> getPosition(positionId) }
                .toList()
        }
    }

    private fun convertPosition(company: Company, positionDto: PositionDto) =
        Position(
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