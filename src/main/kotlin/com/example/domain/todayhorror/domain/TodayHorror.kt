package com.example.domain.todayhorror.domain

import com.example.domain.report.Report
import com.example.domain.user.User
import com.example.global.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_today_horror")
class TodayHorror(
    @ManyToOne
    val user: User,
    @ManyToOne
    val report: Report,

    ): BaseEntity() {

}