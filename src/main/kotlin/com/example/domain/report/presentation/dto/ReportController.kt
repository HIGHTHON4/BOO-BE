package com.example.domain.report.presentation.dto

import com.example.domain.report.enum.Sort
import com.example.domain.report.service.QueryAllMyReportService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/report")
class ReportController(
    private val queryAllMyReportService: QueryAllMyReportService
) {
    @GetMapping("/my")
    fun queryAllReports(@RequestParam sort: Sort, @RequestParam ai: List<UUID>) = queryAllMyReportService.execute(sort, ai)

}