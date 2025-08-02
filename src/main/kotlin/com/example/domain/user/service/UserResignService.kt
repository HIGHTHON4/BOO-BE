package com.example.debatematch.domain.user.service

import com.example.debatematch.domain.user.facade.UserFacade
import com.example.debatematch.domain.user.persistence.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserResignService(
    private val userRepository: UserRepository,
    private val userFacade: UserFacade
) {
    @Transactional
    fun execute() {
        val user = userFacade.currentUser()
        user.id?.let { userRepository.deleteById(it) }
    }
}
