package com.example.domain.chat.persistance

import com.example.domain.chat.Chat
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ChatRepository : JpaRepository<Chat, UUID> {
    fun findAllByReportIdOrderByCreatedAt(reportId: UUID): List<Chat>
    fun findAllByReportId(reportId: UUID): List<Chat>
}
