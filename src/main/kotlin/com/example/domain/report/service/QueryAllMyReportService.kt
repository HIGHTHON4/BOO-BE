package com.example.domain.report.service

import com.example.debatematch.domain.user.facade.UserFacade
import com.example.domain.report.enum.Sort
import com.example.domain.report.persistance.ReportRepository
import com.example.domain.report.presentation.dto.res.QueryAllMyReportResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class QueryAllMyReportService(
    private val reportRepository: ReportRepository,
    private val userFacade: UserFacade
) {
    @Transactional(readOnly = true)
    fun execute(sort: Sort, ai: List<UUID>): List<QueryAllMyReportResponse>{
        val user = userFacade.currentUser()

        return reportRepository.findAllByUserId(userId = user.id!!)
            .filter { it.ai.id != null && it.ai.id in ai }
            .sortedBy {
                when (sort) {
                    Sort.TIME -> it.createdAt
                    Sort.LEVEL -> it.fearLevel ?: 0
                } as Nothing
            }.map { QueryAllMyReportResponse(level = it.fearLevel!!, date = it.createdAt!!.toLocalDate(), title = it.title!! ) }
    }

}