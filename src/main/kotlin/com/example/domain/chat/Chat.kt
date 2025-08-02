package com.example.domain.chat

import com.example.domain.chat.enum.Sender
import com.example.domain.report.Report
import com.example.global.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_chat")
class Chat(
    @Column(length = 2000)
    val content: String,
    @Enumerated(EnumType.STRING)
    val sender: Sender,
    @ManyToOne(fetch = FetchType.EAGER)
    val report: Report
) : BaseEntity()
