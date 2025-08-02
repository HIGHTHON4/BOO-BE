package com.example.domain.todayhorror.service

import com.example.domain.chat.exception.ReportNotFoundException
import com.example.domain.report.persistance.ReportRepository
import com.example.domain.todayhorror.presentation.dto.res.QueryHorrorResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class QueryHorrorService(
    private val reportRepository: ReportRepository
) {
    @Transactional(readOnly = true)
    fun execute(reportId: UUID): QueryHorrorResponse {
        val report = reportRepository.findById(reportId).orElseThrow { ReportNotFoundException }
        return QueryHorrorResponse(text = report.horrorStory!!, content = report.content!!, title = report.title!!)
    }
}
