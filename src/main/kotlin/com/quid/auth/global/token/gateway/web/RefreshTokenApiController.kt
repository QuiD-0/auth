package com.quid.auth.global.token.gateway.web

import com.quid.auth.global.api.ApiResponse
import com.quid.auth.global.api.Success
import com.quid.auth.global.token.gateway.web.request.RefreshTokenRequest
import com.quid.auth.global.token.usecase.RefreshAccessToken
import com.quid.auth.global.token.gateway.web.response.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/token")
class RefreshTokenApiController(
    private val refreshAccessToken: RefreshAccessToken,
) {

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): ApiResponse<TokenResponse> =
        Success { refreshAccessToken(request.accessToken, request.refreshToken) }
}