package com.example.domain.report.presentation.dto.res

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.UUID

data class QueryAllMyReportResponse(
    val level: Comparable<*>,
    @JsonFormat(pattern = "yyyy.MM.dd")
    val date: LocalDate,
    val title: String,
    val reportId: UUID
)
