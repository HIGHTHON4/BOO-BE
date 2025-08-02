package com.example.domain.chat.service

import com.example.domain.ai.presentation.dto.req.GeminiContent
import com.example.domain.ai.presentation.dto.req.GeminiPart
import com.example.domain.ai.presentation.dto.req.GeminiRequest
import com.example.domain.ai.presentation.dto.res.GeminiResultDetail
import com.example.domain.ai.presentation.dto.res.GeminiResultDetail2
import com.example.domain.ai.properties.GeminiProperties
import com.example.domain.chat.exception.ReportNotFoundException
import com.example.domain.chat.persistance.ChatRepository
import com.example.domain.chat.presentation.dto.req.ChatBotRequest
import com.example.domain.chat.presentation.dto.req.StopChatRequest
import com.example.domain.chat.prompt.MakeReportPrompt
import com.example.domain.report.enum.FearLevel
import com.example.domain.report.enum.Status
import com.example.domain.report.persistance.ReportRepository
import com.example.infra.feign.gemini.GeminiClient
import kotlinx.serialization.json.Json
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StopChatService(
    private val chatRepository: ChatRepository,
    private val reportRepository: ReportRepository,
    private val geminiClient: GeminiClient,
    private val geminiProperties: GeminiProperties,
    private val makeReportPrompt: MakeReportPrompt,
    private val eventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun execute(request: StopChatRequest): GeminiResultDetail2 {
        val report = reportRepository.findById(request.reportId).orElseThrow { ReportNotFoundException }
        val chats = chatRepository.findAllByReportId(report.id!!)
        val ai = report.ai

        val response = geminiClient.generateContent(
            apiKey = geminiProperties.apiKey,
            request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(
                            GeminiPart(text = ai.prompt + makeReportPrompt.prompt + chats.map { ChatBotRequest(content = it.content, sender = it.sender) })
                        )
                    )
                )
            )
        )

        val summary = Json.decodeFromString<GeminiResultDetail>(response.candidates[0].content.parts[0].text)

        report.title = summary.title
        report.fearLevel = FearLevel.F.getFearLevel(summary.fearLevel)
        report.content = summary.summary
        report.status = Status.DONE

        eventPublisher.publishEvent(StopChatRequest(report.id!!))

        return GeminiResultDetail2(summary = summary.summary, fearLevel = report.fearLevel!!, title = summary.title)
    }
}
