package com.example.domain.chat.presentation.dto.res

import com.example.domain.chat.enum.Sender

data class QueryChatHistoryResponse(
    val sender: Sender,
    val content: String
)
