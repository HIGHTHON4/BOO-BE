package com.example.infra.feign.gemini

import com.example.domain.ai.presentation.dto.req.GeminiRequest
import com.example.domain.ai.presentation.dto.res.GeminiResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "neis-feign-client", url = "https://generativelanguage.googleapis.com/v1beta")
interface GeminiClient {
    @PostMapping("/models/gemini-2.5-flash:generateContent")
    fun generateContent(
        @RequestParam("key") apiKey: String,
        @RequestBody request: GeminiRequest
    ): GeminiResponse

    @PostMapping("/models/gemini-2.5-flash:generateContent")
    fun generateReport(
        @RequestParam("key") apiKey: String,
        @RequestBody request: GeminiRequest
    ): String
}
