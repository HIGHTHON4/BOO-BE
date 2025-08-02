package com.example.global.security.jwt.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object InvaildJwtException : CustomException(
    ErrorCode.INVALID_TOKEN
)
