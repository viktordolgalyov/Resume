package com.dolgalyov.resume.data.position.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.common.util.toDateOrNull
import com.dolgalyov.resume.data.position.api.PositionApi
import com.dolgalyov.resume.data.position.api.RawPosition
import com.dolgalyov.resume.data.position.model.PositionDto
import io.reactivex.Single
import java.util.*

class PositionRemoteSource(private val api: PositionApi) :
    RemoteDataSource<String, PositionDto> {

    override fun getById(id: String): Single<PositionDto> {
        return api.getPositions().map { response ->
            val raw = response.firstOrNull { it.id == id }
            raw?.run { convertToDto(this) } ?: throw ItemNotFoundException(
                id
            )
        }
    }

    private fun convertToDto(raw: RawPosition) =
        PositionDto(
            id = raw.id,
            companyId = raw.companyId,
            name = raw.name,
            description = raw.description,
            from = raw.fromDate.toDateOrNull() ?: Date(),
            to = raw.toDate?.toDateOrNull()
        )
}