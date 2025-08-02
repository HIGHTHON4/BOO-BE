package com.example.domain.report.presentation.dto.res

import com.example.domain.report.enum.FearLevel
import java.time.LocalDate

data class QueryAllMyReportResponse(
    val level: FearLevel,
    val date: LocalDate,
    val title: String
)
