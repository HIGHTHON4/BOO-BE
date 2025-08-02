package com.example.debatematch.domain.user.service

import com.example.debatematch.domain.user.exception.PasswordMissMatch
import com.example.debatematch.domain.user.exception.UserNotFoundException
import com.example.debatematch.domain.user.persistence.UserRepository
import com.example.debatematch.domain.user.presentation.dto.UserLoginRequest
import com.example.global.security.jwt.JwtTokenProvider
import com.example.global.security.jwt.response.TokenResponse
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserLoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional
    fun execute(request: UserLoginRequest): TokenResponse {
        val user = userRepository.findByAccountId(request.accountId) ?: throw UserNotFoundException

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordMissMatch
        }

        return jwtTokenProvider.generateToken(user.accountId)
    }
}
