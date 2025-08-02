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
            .filter {  ai.contains(it.ai.id) }
            .sortedWith(
                when (sort) {
                    Sort.TIME -> compareBy { it.createdAt }
                    Sort.LEVEL -> compareByDescending { it.fearLevel ?: 0 }
                    Sort.LAST -> compareBy{it.fearLevel ?: 0}
                }
            )
            .map {
                QueryAllMyReportResponse(
                    level = it.fearLevel ?: 0,
                    date = it.createdAt?.toLocalDate() ?: LocalDate.now(),
                    title = it.title.orEmpty(),
                    reportId = it.id!!
                )
            }
    }

}