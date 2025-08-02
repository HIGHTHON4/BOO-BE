package com.example.domain.ghoststory

import com.example.domain.report.Report
import com.example.global.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_ghoststory")
class GhostStory(
    val title: String,
    @Column(length = 2000)
    val content: String,

    @ManyToOne
    val report: Report
) : BaseEntity()
