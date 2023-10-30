package com.quid.auth.user.gateway.web

import com.quid.auth.global.api.ApiResponse
import com.quid.auth.global.api.Success
import com.quid.auth.user.gateway.web.request.SignUpRequest
import com.quid.auth.user.gateway.web.response.UserResponse
import com.quid.auth.user.usecase.SignUp
import org.springframework.web.bind.annotation.GetMapping
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
    fun userSignUp(@RequestBody request: SignUpRequest): ApiResponse<UserResponse> =
        Success { signUp(request.toUser()) }

    @GetMapping("/hello")
    fun hello(): ApiResponse<String> =
        Success { "hello" }

}