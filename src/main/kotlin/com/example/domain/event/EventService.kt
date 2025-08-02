package com.example.domain.event

import com.example.domain.ai.presentation.dto.req.GeminiContent
import com.example.domain.ai.presentation.dto.req.GeminiPart
import com.example.domain.ai.presentation.dto.req.GeminiRequest
import com.example.domain.ai.properties.GeminiProperties
import com.example.domain.chat.exception.ReportNotFoundException
import com.example.domain.chat.persistance.ChatRepository
import com.example.domain.chat.presentation.dto.req.ChatBotRequest
import com.example.domain.chat.presentation.dto.req.StopChatRequest
import com.example.domain.chat.prompt.MakeHorrorStoryPrompt
import com.example.domain.report.persistance.ReportRepository
import com.example.infra.feign.gemini.GeminiClient
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EventService(
    private val chatRepository: ChatRepository,
    private val reportRepository: ReportRepository,
    private val geminiClient: GeminiClient,
    private val geminiProperties: GeminiProperties,
    private val makeHorrorStoryPrompt: MakeHorrorStoryPrompt
) {

    @EventListener(StopChatRequest::class)
    @Transactional
    fun event(request: StopChatRequest) {
        var report = reportRepository.findById(request.reportId).orElseThrow { ReportNotFoundException }
        val chats = chatRepository.findAllByReportId(reportId = request.reportId)
        val horrorStory = geminiClient.generateContent(
            apiKey = geminiProperties.apiKey,
            request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(
                            GeminiPart(text = makeHorrorStoryPrompt.prompt + chats.map { ChatBotRequest(content = it.content, sender = it.sender) })
                        )
                    )
                )
            )
        )

        val text = horrorStory.candidates[0].content.parts[0].text

        report.horrorStory = text
    }
}
