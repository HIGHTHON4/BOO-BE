package com.example.global.error.exception

abstract class CustomException(
    val errorCode: ErrorCode
) : RuntimeException()
