package com.example.domain.todayhorror.persistence

import com.example.domain.todayhorror.domain.TodayHorror
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TodayHorrorRepository : JpaRepository<TodayHorror, UUID> {
    fun findAllByUserId(userId: UUID): List<TodayHorror>
}
