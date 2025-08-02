package com.example.domain.chat.presentation.dto.req

import java.util.UUID

data class ChatRequest(
    val text: String,
    val reportId: UUID
)
