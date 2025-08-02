package com.example.domain.chat.service

import com.example.debatematch.domain.user.facade.UserFacade
import com.example.debatematch.domain.user.persistence.UserRepository
import com.example.domain.ai.presentation.dto.req.GeminiContent
import com.example.domain.ai.presentation.dto.req.GeminiPart
import com.example.domain.ai.presentation.dto.req.GeminiRequest
import com.example.domain.ai.properties.GeminiProperties
import com.example.domain.chat.enum.Sender
import com.example.domain.chat.exception.ReportNotFoundException
import com.example.domain.chat.persistance.ChatRepository
import com.example.domain.chat.presentation.dto.req.ChatBotRequest
import com.example.domain.chat.presentation.dto.req.StopChatRequest
import com.example.domain.chat.prompt.MakeReportPrompt
import com.example.domain.report.persistance.ReportRepository
import com.example.infra.feign.gemini.GeminiClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StopChatService(
    private val chatRepository: ChatRepository,
    private val reportRepository: ReportRepository,
    private val userFacade: UserFacade,
    private val geminiClient: GeminiClient,
    private val geminiProperties: GeminiProperties,
    private val makeReportPrompt: MakeReportPrompt


) {
    @Transactional
    fun execute(request: StopChatRequest){
        val report = reportRepository.findById(request.reportId).orElseThrow{ ReportNotFoundException }
        val chats = chatRepository.findAllByReportId(report.id!!)
        val ai = report.ai

        val response = geminiClient.generateContent(
            apiKey = geminiProperties.apiKey,
            request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(
                            GeminiPart(text = ai.prompt + makeReportPrompt.prompt + chats.map { ChatBotRequest(content = it.content, sender = it.sender) }),
                        )
                    )
                )
            )
        )

        println(response)

    }
}