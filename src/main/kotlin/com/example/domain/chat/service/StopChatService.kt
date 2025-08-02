package com.example.domain.chat.service

import com.example.debatematch.domain.user.facade.UserFacade
import com.example.domain.ai.presentation.dto.req.GeminiContent
import com.example.domain.ai.presentation.dto.req.GeminiPart
import com.example.domain.ai.presentation.dto.req.GeminiRequest
import com.example.domain.ai.presentation.dto.res.GeminiResultDetail
import com.example.domain.ai.properties.GeminiProperties
import com.example.domain.chat.exception.ReportNotFoundException
import com.example.domain.chat.persistance.ChatRepository
import com.example.domain.chat.presentation.dto.req.ChatBotRequest
import com.example.domain.chat.presentation.dto.req.StopChatRequest
import com.example.domain.chat.prompt.MakeHorrorStoryPrompt
import com.example.domain.chat.prompt.MakeReportPrompt
import com.example.domain.report.enum.FearLevel
import com.example.domain.report.persistance.ReportRepository
import com.example.infra.feign.gemini.GeminiClient
import kotlinx.serialization.json.Json
import org.springframework.boot.jackson.JsonMixinModuleEntries
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StopChatService(
    private val chatRepository: ChatRepository,
    private val reportRepository: ReportRepository,
    private val geminiClient: GeminiClient,
    private val geminiProperties: GeminiProperties,
    private val makeReportPrompt: MakeReportPrompt,
    private val makeHorrorStoryPrompt: MakeHorrorStoryPrompt

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

        val summary = Json.decodeFromString<GeminiResultDetail>(response.candidates[0].content.parts[0].text)

        report.title = summary.title
        report.fearLevel = FearLevel.F.getFearLevel(summary.fearLevel)
        report.content = summary.summary

        val horrorStory = geminiClient.generateContent(
            apiKey = geminiProperties.apiKey,
            request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(
                            GeminiPart(text =makeHorrorStoryPrompt.prompt + chats.map { ChatBotRequest(content = it.content, sender = it.sender) }),
                        )
                    )
                )
            )
        )

        val text = horrorStory.candidates[0].content.parts[0].text

        report.horrorStory = text

    }
}