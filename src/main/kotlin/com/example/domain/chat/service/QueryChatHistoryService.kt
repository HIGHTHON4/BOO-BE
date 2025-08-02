package com.example.domain.chat.service

import com.example.domain.chat.persistance.ChatRepository
import com.example.domain.chat.presentation.dto.req.QueryChatHistoryRequest
import com.example.domain.chat.presentation.dto.res.QueryChatHistoryResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryChatHistoryService(
    private val chatRepository: ChatRepository,

) {
    @Transactional(readOnly = true)
    fun execute(request: QueryChatHistoryRequest): List<QueryChatHistoryResponse> {
        val chats = chatRepository.findAllByReportIdOrderByCreatedAt(request.reportId)

        return chats.map { QueryChatHistoryResponse(sender = it.sender, content = it.content) }
    }
}