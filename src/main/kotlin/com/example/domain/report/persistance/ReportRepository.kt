package com.example.domain.report.persistance

import com.example.domain.report.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface ReportRepository : JpaRepository<Report, UUID>{
    fun findAllByUserId(userId: UUID): List<Report>

    @Query(value = "SELECT * FROM tbl_report WHERE user_id != :userId AND status = 'DONE' ORDER BY RAND() LIMIT 3", nativeQuery = true)
    fun findRandom3ByUserId(@Param("userId") userId: UUID): List<Report>
}

