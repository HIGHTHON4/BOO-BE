package com.example.infra.schadular

import com.example.debatematch.domain.user.persistence.UserRepository
import com.example.domain.report.persistance.ReportRepository
import com.example.domain.report.service.QueryReportService
import com.example.domain.todayhorror.domain.TodayHorror
import com.example.domain.todayhorror.persistence.TodayHorrorRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ScheduleService(
    private val reportRepository: ReportRepository,
    private val userRepository: UserRepository,
    private val todayHorrorRepository: TodayHorrorRepository

    ) {

    @Scheduled(cron = "0 * * * * ?", zone = "Asia/Seoul")
    @Transactional
    fun todayHorror() {
        todayHorrorRepository.deleteAll()

        val users = userRepository.findAll()
        users.forEach { user ->
            val reports = reportRepository.findRandom3ByUserId(user.id!!)
            reports.forEach { report ->
                todayHorrorRepository.save(TodayHorror(user = user, report = report))
            }
        }
    }
}