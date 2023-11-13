package com.quid.auth.authentication.user.gateway.web

import com.quid.auth.authentication.user.domain.UserDetail
import com.quid.auth.authentication.user.gateway.web.request.LogInRequest
import com.quid.auth.authentication.user.gateway.web.request.SignUpRequest
import com.quid.auth.authentication.user.usecase.LogIn
import com.quid.auth.authentication.user.usecase.SignUp
import com.quid.auth.global.api.ApiResponse
import com.quid.auth.global.api.Success
import com.quid.auth.token.gateway.web.response.TokenResponse
import com.quid.auth.token.usecase.LogOut
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserApiController(
    val signUp: SignUp,
    val logIn: LogIn,
    val logOut: LogOut
) {

    @PostMapping("/signup")
    fun userSignUp(@RequestBody request: SignUpRequest): ApiResponse<Unit> =
        Success { signUp(request.toUser()) }

    @PostMapping("/login")
    fun userLogIn(@RequestBody request: LogInRequest): ApiResponse<TokenResponse> =
        Success { logIn(request.username, request.password) }

    @PostMapping("/logout")
    fun userLogOut(@AuthenticationPrincipal userDetail: UserDetail): ApiResponse<Unit> =
        Success { logOut(userDetail.username) }

}