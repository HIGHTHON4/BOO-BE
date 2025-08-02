package com.example.domain.chat.presentation

import com.example.domain.chat.presentation.dto.req.ChatRequest
import com.example.domain.chat.presentation.dto.req.StartChatRequest
import com.example.domain.chat.service.ChatService
import com.example.domain.chat.service.StartChatService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatController(
    private val startChatService: StartChatService,
    private val chatService: ChatService,
) {
    @PostMapping("/start")
    private fun startChat(@RequestBody request: StartChatRequest) = startChatService.execute(request)

    @PostMapping()
    fun execute(@RequestBody request: ChatRequest) = chatService.execute(request)
}