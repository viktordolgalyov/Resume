package com.dolgalyov.resume.data.company

import com.dolgalyov.resume.common.arch.data.AbstractRepository
import com.dolgalyov.resume.data.company.model.CompanyDto

typealias CompanyRepository = AbstractRepository<String, CompanyDto>