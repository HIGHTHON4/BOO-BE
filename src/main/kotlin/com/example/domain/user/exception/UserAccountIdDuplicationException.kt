package com.example.domain.user.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object UserAccountIdDuplicationException : CustomException(
    ErrorCode.DUPLICATE_USER
)
