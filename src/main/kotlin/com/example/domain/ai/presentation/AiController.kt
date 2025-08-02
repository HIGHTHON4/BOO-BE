package com.example.domain.ai.presentation

import com.example.domain.ai.service.AiQueryAllService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ai")
class AiController(
    private val aiQueryAllService: AiQueryAllService,
) {
    @GetMapping
    fun getAllAi() = aiQueryAllService.execute()
}