package com.dolgalyov.resume.data.education.source

import com.dolgalyov.resume.common.arch.data.RemoteDataSource
import com.dolgalyov.resume.common.exception.ItemNotFoundException
import com.dolgalyov.resume.common.util.toDateOrNull
import com.dolgalyov.resume.data.education.api.EducationApi
import com.dolgalyov.resume.data.education.api.RawEducationRecord
import com.dolgalyov.resume.data.education.model.EducationRecordDto
import java.util.*

class EducationRemoteSource(
    private val api: EducationApi
) : RemoteDataSource<String, EducationRecordDto> {

    override suspend fun getById(id: String): EducationRecordDto {
        val records = api.getEducationRecords()
        val record = records.firstOrNull { it.id == id }
        return record?.run(::convertToDto) ?: throw  ItemNotFoundException(id)
    }

    private fun convertToDto(raw: RawEducationRecord) = EducationRecordDto(
        id = raw.id,
        universityId = raw.universityId,
        from = raw.fromDate.toDateOrNull() ?: Date(),
        to = raw.toDate.toDateOrNull() ?: Date(),
        degree = raw.degree
    )
}