package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableJpaAuditing
@ConfigurationPropertiesScan
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
