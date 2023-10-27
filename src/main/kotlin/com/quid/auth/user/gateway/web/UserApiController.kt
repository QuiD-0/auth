package com.quid.auth.user.gateway.web

import com.quid.auth.global.ApiResponse
import com.quid.auth.global.Success
import com.quid.auth.user.gateway.web.request.SignUpRequest
import com.quid.auth.user.usecase.SignUp
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserApiController(
    val signUp: SignUp
) {

    @PostMapping("/signup")
    fun signupRequest(@RequestBody request: SignUpRequest): ApiResponse<Unit> =
        Success { signUp(request.toUserInfo()) }

}