package com.example.debatematch.domain.user.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object UserDuplicationException : CustomException(
    ErrorCode.DUPLICATED_USER
)
