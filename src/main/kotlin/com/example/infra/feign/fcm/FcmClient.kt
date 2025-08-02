package com.example.infra.feign.fcm

import com.example.domain.fcm.FcmMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "fcm", url = "\${firebase.messaging.url.host}")
interface FcmClient {

    @PostMapping("\${firebase.messaging.url.path}")
    fun sendMessage(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: FcmMessage
    )
}
