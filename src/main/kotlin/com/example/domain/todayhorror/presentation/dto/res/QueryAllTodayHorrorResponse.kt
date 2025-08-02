package com.example.domain.todayhorror.presentation.dto.res

import java.util.UUID

data class QueryAllTodayHorrorResponse(
    val title: String,
    val reportId: UUID
)
