package com.example.domain.report.persistance

import com.example.domain.report.Report
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReportRepository : JpaRepository<Report, UUID>
