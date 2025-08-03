package com.example.infra.feign.fcm

import com.example.domain.fcm.FcmMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "fcm", url = "https://fcm.googleapis.com/v1/projects/boo-9b814/messages:send")
interface FcmClient {

    @PostMapping(
        headers = ["Content-Type=application/json"]
    )
    fun sendMessage(
        @RequestHeader("Authorization") authorization: String,
        @RequestBody request: FcmMessage
    )
}
