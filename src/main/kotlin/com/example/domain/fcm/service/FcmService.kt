package com.example.domain.fcm.service

import com.example.domain.fcm.FcmMessage
import com.example.infra.feign.fcm.FcmClient
import com.example.infra.google.GoogleOauthService
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val fcmClient: FcmClient,
    private val googleOauthService: GoogleOauthService
) {

    fun execute(deviceTokens: List<String>, title: String, body: String) {
        val token = googleOauthService.getToken()
        deviceTokens.filter { it.isNotBlank() }
            .forEach {
                sendMessage(generateMessage(it, title, body), token)
            }
    }

    private fun sendMessage(fcmMessage: FcmMessage, token: String) {
        fcmClient.sendMessage(authorization = "Bearer $token", request =  fcmMessage)
    }

    private fun generateMessage(
        deviceToken: String,
        title: String,
        body: String
    ): FcmMessage {
        val notification = FcmMessage.Notification(title = title, body = body)
        val message = FcmMessage.Message(token = deviceToken, notification = notification)
        return FcmMessage(message = message)
    }

    private fun saveMessage() {
        TODO("알림 리스트 조회가 생길 시 알림 테이블과 함께 saveMessage 생성해야함")
    }
}
