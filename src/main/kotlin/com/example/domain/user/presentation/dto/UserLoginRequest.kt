package com.example.debatematch.domain.user.presentation.dto

import com.example.domain.user.enum.OS

data class UserLoginRequest(
    val accountId: String,
    val password: String,
    val deviceToken: String,
    val os: OS
)
