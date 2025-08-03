package com.example.global.config

import com.google.auth.oauth2.GoogleCredentials
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.net.URL
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

@Configuration
class FcmConfig(
    @Value("\${firebase.messaging.url.key}")
    private val url: URL
) {
    @PostConstruct
    fun init() {
        try {
            url.openStream().use { serviceAccount ->
                val options = FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build()

                if (FirebaseApp.getApps().isEmpty()) {
                    FirebaseApp.initializeApp(options)
                }
            }
        } catch (e: Exception) {
        }
    }
}
