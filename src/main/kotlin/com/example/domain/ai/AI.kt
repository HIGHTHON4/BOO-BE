package com.example.domain.ai

import com.example.global.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_ai")
class AI(
    val name: String,
    val description: String,
    @Column(length = 1000)
    val prompt: String
) : BaseEntity()
