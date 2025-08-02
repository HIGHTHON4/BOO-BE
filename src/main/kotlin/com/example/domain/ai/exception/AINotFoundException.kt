package com.example.domain.ai.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object AINotFoundException:CustomException(
    ErrorCode.AI_NOT_FOUND
) {
}