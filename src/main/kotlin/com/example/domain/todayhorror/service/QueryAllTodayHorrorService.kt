package com.example.domain.todayhorror.service

import com.example.debatematch.domain.user.facade.UserFacade
import com.example.domain.todayhorror.persistence.TodayHorrorRepository
import com.example.domain.todayhorror.presentation.dto.res.QueryAllTodayHorrorResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAllTodayHorrorService(
    private val todayHorrorRepository: TodayHorrorRepository,
    private val userFacade: UserFacade
) {
    @Transactional(readOnly = true)
    fun execute():List<QueryAllTodayHorrorResponse>{
        val user = userFacade.currentUser()

        return todayHorrorRepository.findAllByUserId(user.id!!).map { QueryAllTodayHorrorResponse(title = it.report.title!!, reportId = it.report.id!!) }
    }
}