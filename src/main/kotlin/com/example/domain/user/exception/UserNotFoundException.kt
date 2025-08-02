package com.example.debatematch.domain.user.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object UserNotFoundException : CustomException(
    ErrorCode.USER_NOT_FOUND
)
