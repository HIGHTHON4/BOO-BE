package com.example.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("auth.jwt")
class JwtProperties(
    val secretKey: String,
    val accessExp: Long,
    val header: String,
    val prefix: String
)
