package com.example.debatematch.domain.user.presentation

import com.example.debatematch.domain.user.service.UserLoginService
import com.example.debatematch.domain.user.service.UserResignService
import com.example.debatematch.domain.user.service.UserSignUpService
import com.example.domain.user.presentation.dto.UserLoginRequest
import com.example.domain.user.presentation.dto.UserSignUpRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(
    private val userSignUpService: UserSignUpService,
    private val userResignService: UserResignService,
    private val userLoginService: UserLoginService

) {
    @PostMapping("/signup")
    fun signUp(
        @RequestBody userSignUpRequest: UserSignUpRequest
    ): UUID? {
        return userSignUpService.execute(userSignUpRequest)
    }

    @DeleteMapping("/resign")
    fun resign() {
        userResignService.execute()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: UserLoginRequest
    ) = userLoginService.execute(request)
}
