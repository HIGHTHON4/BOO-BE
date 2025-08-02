package com.example.domain.chat.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object ReportNotFoundException:CustomException(
    ErrorCode.REPORT_NOT_FOUND
) {
}