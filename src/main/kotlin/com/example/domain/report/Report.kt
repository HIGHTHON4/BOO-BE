package com.example.domain.report

import com.example.domain.ai.AI
import com.example.domain.report.enum.FearLevel
import com.example.domain.user.User
import com.example.global.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_report")
class Report(
    var title: String? = null,
    @Column(length = 2000)
    var content: String? = null,//내용 + 등급 설명
    var fearLevel: FearLevel? = null,
    @Column(length = 2000)
    var horrorStory: String? = null,


    @OneToOne(fetch = FetchType.EAGER)
    val ai: AI,
    @ManyToOne(fetch = FetchType.EAGER)
    val user: User
): BaseEntity()