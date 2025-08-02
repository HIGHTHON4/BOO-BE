package com.example.debatematch.domain.user.persistence

import com.example.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByAccountId(accountId: String): User?

    fun existsUserByAccountId(accountId: String): Boolean
}
