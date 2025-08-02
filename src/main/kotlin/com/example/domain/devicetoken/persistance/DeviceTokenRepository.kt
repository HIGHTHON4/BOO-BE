package com.example.domain.devicetoken.persistance

import com.example.domain.devicetoken.DeviceToken
import com.example.domain.user.enum.OS
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DeviceTokenRepository: JpaRepository<DeviceToken, UUID> {
    fun findByUserIdAndOs(userId: UUID, os: OS): DeviceToken?
    fun existsByUserIdAndOs(userId: UUID, os: OS): Boolean
}