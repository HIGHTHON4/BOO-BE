package com.example.domain.chat.presentation.dto.req

import com.example.domain.chat.enum.Sender
import java.util.UUID

data class StartChatRequest (
    val aiId: UUID
)