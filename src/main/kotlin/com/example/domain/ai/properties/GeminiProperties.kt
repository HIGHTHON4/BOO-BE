package com.example.domain.ai.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class GeminiProperties (
    @Value("\${gemini.api-key}")
    val apiKey: String
)