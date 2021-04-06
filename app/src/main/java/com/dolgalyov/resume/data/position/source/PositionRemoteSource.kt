package com.dolgalyov.resume.data.position.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.common.util.toDateOrNull
import com.dolgalyov.resume.data.position.api.PositionApi
import com.dolgalyov.resume.data.position.api.RawPosition
import com.dolgalyov.resume.data.position.model.PositionDto
import java.util.*

class PositionRemoteSource(
    private val api: PositionApi
) : RemoteDataSource<String, PositionDto> {

    override suspend fun getById(id: String): PositionDto {
        val positions = api.getPositions()
        val position = positions.firstOrNull { it.id == id }
        return position?.run(::convertToDto) ?: throw ItemNotFoundException(id)
    }

    private fun convertToDto(raw: RawPosition) = PositionDto(
        id = raw.id,
        companyId = raw.companyId,
        name = raw.name,
        description = raw.description,
        from = raw.fromDate.toDateOrNull() ?: Date(),
        to = raw.toDate?.toDateOrNull()
    )
}