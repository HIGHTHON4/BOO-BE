package com.example.domain.devicetoken.exception

import com.example.global.error.exception.CustomException
import com.example.global.error.exception.ErrorCode

object DeviceTokenNotFound:CustomException(
    ErrorCode.DEVICE_TOKEN_NOT_FOUND
) {
}