package com.example.domain.user.presentation.dto

import com.example.domain.device.enum.OS

data class UserSignUpRequest(
    val accountId: String,
    val password: String,
    val deviceToken: String,
    val os: OS
)
