package com.example.domain.report.service

import com.example.domain.ai.presentation.dto.res.GeminiResultDetail2
import com.example.domain.chat.exception.ReportNotFoundException
import com.example.domain.report.persistance.ReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class QueryReportService(
    private val reportRepository: ReportRepository
) {
    @Transactional(readOnly = true)
    fun execute(reportId: UUID): GeminiResultDetail2 {
        val report = reportRepository.findById(reportId).orElseThrow { ReportNotFoundException }
        return GeminiResultDetail2(summary = report.content!!, title = report.title!!, fearLevel = report.fearLevel!!, aiName = report.ai.name)
    }
}
