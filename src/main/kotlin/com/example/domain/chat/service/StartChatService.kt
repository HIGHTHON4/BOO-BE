package com.example.domain.chat.service

import com.example.debatematch.domain.user.facade.UserFacade
import com.example.domain.ai.exception.AINotFoundException
import com.example.domain.ai.persistance.AiRepository
import com.example.domain.chat.presentation.dto.req.StartChatRequest
import com.example.domain.report.Report
import com.example.domain.report.persistance.ReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StartChatService(
    private val userFacade: UserFacade,
    private val reportRepository: ReportRepository,
    private val aiRepository: AiRepository
) {
    @Transactional
    fun execute(request: StartChatRequest): UUID {
        val user = userFacade.currentUser()
        val ai = aiRepository.findById(request.aiId).orElseThrow { AINotFoundException }

        return reportRepository.save(Report(user = user, ai = ai)).id!!
    }
}
