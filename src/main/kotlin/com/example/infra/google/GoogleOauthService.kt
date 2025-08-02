package com.example.infra.google

import com.example.infra.util.RedisUtil
import com.google.auth.oauth2.GoogleCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL

@Component
class GoogleOauthService(
    private val redisUtil: RedisUtil,
    @Value("\${firebase.messaging.url.key}") private val key: URL,
    @Value("\${firebase.messaging.url.credentials}") private val credentials: String
)  {
    companion object {
        private const val REDIS_KEY = "fcm:access-token"
    }
    fun getToken(): String {
        val cached = redisUtil.getData(REDIS_KEY)
        if (!cached.isNullOrBlank()) return cached

        return try {
            val credentials = GoogleCredentials
                .fromStream(key.openStream())
                .createScoped(credentials)

            credentials.refreshIfExpired()
            val token = credentials.accessToken.tokenValue

            redisUtil.setDataExpire(REDIS_KEY, token, 3600)
            token
        } catch (e: Exception) {
            throw RuntimeException("비상상황 삐용삐용", e)
        }
    }
}
