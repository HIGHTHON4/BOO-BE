package com.example.domain.chat.presentation.dto.req

import com.example.domain.chat.enum.Sender

data class ChatBotRequest(
    val content: String,
    val sender: Sender,
)
