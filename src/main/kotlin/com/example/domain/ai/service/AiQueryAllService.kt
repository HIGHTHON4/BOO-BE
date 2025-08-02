package com.example.domain.ai.service

import com.example.domain.ai.persistance.AiRepository
import com.example.domain.ai.presentation.dto.res.AiQueryAllResponse
import org.springframework.stereotype.Service

@Service
class AiQueryAllService(
    private val aiRepository: AiRepository
) {
    fun execute(): List<AiQueryAllResponse>{
        return aiRepository.findAll().map { AiQueryAllResponse(name = it.name, description = it.description, id = it.id!!) }
    }
}