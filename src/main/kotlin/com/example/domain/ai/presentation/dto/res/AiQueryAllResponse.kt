package com.example.domain.ai.presentation.dto.res

import java.util.UUID

data class AiQueryAllResponse(
    val name: String,
    val description: String,
    val id: UUID
)
