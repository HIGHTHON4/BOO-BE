package com.example.global.security.jwt.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object ExpiredJwtException : CustomException(
    ErrorCode.EXPIRED_TOKEN
)
