package com.example.debatematch.domain.user.service

import com.example.debatematch.domain.user.exception.PasswordMissMatch
import com.example.debatematch.domain.user.exception.UserNotFoundException
import com.example.debatematch.domain.user.persistence.UserRepository
import com.example.domain.devicetoken.DeviceToken
import com.example.domain.devicetoken.exception.DeviceTokenNotFound
import com.example.domain.devicetoken.persistance.DeviceTokenRepository
import com.example.domain.user.presentation.dto.UserLoginRequest
import com.example.global.security.jwt.JwtTokenProvider
import com.example.global.security.jwt.response.TokenResponse
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserLoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val deviceTokenRepository: DeviceTokenRepository
) {
    @Transactional
    fun execute(request: UserLoginRequest): TokenResponse {
        val user = userRepository.findByAccountId(request.accountId) ?: throw UserNotFoundException
        if (deviceTokenRepository.existsByUserIdAndOs(user.id!!, request.os)) {
            var deviceToken = deviceTokenRepository.findByUserIdAndOs(user.id!!, request.os) ?: throw DeviceTokenNotFound
            deviceToken.device_token = request.deviceToken
        } else {
            deviceTokenRepository.save(DeviceToken(device_token = request.deviceToken, user = user, os = request.os))
        }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw PasswordMissMatch
        }

        return jwtTokenProvider.generateToken(user.accountId)
    }
}
