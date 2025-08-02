package com.example.debatematch.domain.user.presentation.dto

data class UserLoginRequest(
    val accountId: String,
    val password: String
)
