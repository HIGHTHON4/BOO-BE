package com.example.domain.user

import com.example.global.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import lombok.AllArgsConstructor

@Entity
@Table(name = "tbl_user")
@AllArgsConstructor
class User(
    @Column(nullable = false)
    val accountId: String,
    @Column(nullable = false)
    val password: String

) : BaseEntity()
