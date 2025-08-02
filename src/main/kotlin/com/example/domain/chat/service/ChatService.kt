package com.example.domain.chat.service

import com.example.domain.ai.presentation.dto.req.GeminiContent
import com.example.domain.ai.presentation.dto.req.GeminiPart
import com.example.domain.ai.presentation.dto.req.GeminiRequest
import com.example.domain.ai.properties.GeminiProperties
import com.example.domain.chat.Chat
import com.example.domain.chat.enum.Sender
import com.example.domain.chat.exception.ReportNotFoundException
import com.example.domain.chat.persistance.ChatRepository
import com.example.domain.chat.presentation.dto.req.ChatBotRequest
import com.example.domain.chat.presentation.dto.req.ChatRequest
import com.example.domain.chat.presentation.dto.res.ChatResponse
import com.example.domain.chat.prompt.Prompt
import com.example.domain.report.persistance.ReportRepository
import com.example.infra.feign.gemini.GeminiClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatService(
    private val chatRepository: ChatRepository,
    private val geminiClient: GeminiClient,
    private val reportRepository: ReportRepository,
    private val geminiProperties: GeminiProperties,
    private val prompt: Prompt
) {
    @Transactional
    fun execute(request: ChatRequest): ChatResponse {
        val report = reportRepository.findById(request.reportId).orElseThrow { ReportNotFoundException }
        val chats = chatRepository.findAllByReportId(report.id!!)
        val ai = report.ai

        val response = geminiClient.generateContent(
            apiKey = geminiProperties.apiKey,
            request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(
                            GeminiPart(text = ai.prompt + prompt.prompt + chats.map { ChatBotRequest(content = it.content, sender = it.sender) } + ChatBotRequest(content = request.text, sender = Sender.USER))
                        )
                    )
                )
            )
        )
        val text = response.candidates[0].content.parts[0].text

        chatRepository.save(Chat(content = request.text, sender = Sender.USER, report))
        chatRepository.save(Chat(content = text, sender = Sender.BOT, report))

        return ChatResponse(content = text)
    }
}
