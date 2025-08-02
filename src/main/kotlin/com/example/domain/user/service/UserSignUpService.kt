package com.example.debatematch.domain.user.service

import com.example.domain.user.User
import com.example.domain.user.exception.UserAccountIdDuplicationException
import com.example.debatematch.domain.user.persistence.UserRepository
import com.example.domain.devicetoken.DeviceToken
import com.example.domain.devicetoken.persistance.DeviceTokenRepository
import com.example.domain.user.presentation.dto.UserSignUpRequest
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserSignUpService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val deviceTokenRepository: DeviceTokenRepository,
) {
    @Transactional
    fun execute(request: UserSignUpRequest): UUID? {
        if (userRepository.existsUserByAccountId(request.accountId)) {
            throw UserAccountIdDuplicationException
        }


        val user = userRepository.save(
            User(
                accountId = request.accountId,
                password = passwordEncoder.encode(request.password),
            )
        )

        deviceTokenRepository.save(DeviceToken(device_token = request.deviceToken, user=user, os = request.os))

        return user.id
    }
}
