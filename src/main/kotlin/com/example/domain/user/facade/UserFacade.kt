package com.example.debatematch.domain.user.facade

import com.example.domain.user.User
import com.example.debatematch.domain.user.exception.UserNotFoundException
import com.example.debatematch.domain.user.persistence.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository,
) {
    fun currentUser(): User {
        val accountId = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByAccountId(accountId) ?: throw UserNotFoundException
    }

    fun getUserByAccountIdOrThrow(accountId: String): User {
        return userRepository.findByAccountId(accountId) ?: throw UserNotFoundException
    }
}
