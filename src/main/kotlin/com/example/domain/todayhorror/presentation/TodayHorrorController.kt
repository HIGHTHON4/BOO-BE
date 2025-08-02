package com.example.domain.todayhorror.presentation

import com.example.domain.todayhorror.service.QueryAllTodayHorrorService
import com.example.domain.todayhorror.service.QueryHorrorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/today-horror")
class TodayHorrorController(
    private val queryAllTodayHorrorService: QueryAllTodayHorrorService,
    private val queryHorrorService: QueryHorrorService
) {
    @GetMapping()
    fun execute() = queryAllTodayHorrorService.execute()

    @GetMapping("/query")
    fun query(@RequestParam reportId: UUID) = queryHorrorService.execute(reportId)

}