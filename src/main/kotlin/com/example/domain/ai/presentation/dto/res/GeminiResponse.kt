package com.example.domain.ai.presentation.dto.res

data class GeminiResponse(
    val candidates: List<Candidate>,
    val usageMetadata: UsageMetadata,
    val modelVersion: String,
    val responseId: String
)

data class Candidate(
    val content: Content,
    val finishReason: String,
    val index: Int
)

data class Content(
    val parts: List<Part>,
    val role: String
)

data class Part(
    val text: String
)

data class UsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int,
    val promptTokensDetails: List<PromptTokensDetail>,
    val thoughtsTokenCount: Int
)

data class PromptTokensDetail(
    val modality: String,
    val tokenCount: Int
)
