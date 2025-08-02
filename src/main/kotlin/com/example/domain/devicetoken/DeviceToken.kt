package com.example.domain.devicetoken

import com.example.domain.device.enum.OS
import com.example.domain.user.User
import com.example.global.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_device_token")
class DeviceToken(
    var device_token: String,
    @ManyToOne
    val user: User,
    @Enumerated(EnumType.STRING)
    val os: OS
) : BaseEntity()
