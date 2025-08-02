package com.example.domain.ai.persistance

import com.example.domain.ai.AI
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AiRepository : JpaRepository<AI, UUID>
