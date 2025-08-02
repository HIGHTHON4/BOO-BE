package com.example.domain.devicetoken

import com.example.domain.user.User
import com.example.global.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_device_token")
class DeviceToken(
    val device_token: String,
    @ManyToOne
    val user: User
): BaseEntity() {
}