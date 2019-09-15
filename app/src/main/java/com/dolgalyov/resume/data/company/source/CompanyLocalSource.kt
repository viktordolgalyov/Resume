package com.dolgalyov.resume.data.company.source

import com.dolgalyov.resume.common.arch.data.LocalDataSource
import com.dolgalyov.resume.data.company.model.CompanyDto

typealias CompanyLocalSource = LocalDataSource<String, CompanyDto>