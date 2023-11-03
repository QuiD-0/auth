package com.quid.auth.global.token.gateway.web.request

data class RefreshTokenRequest(
    val accessToken: String,
    val refreshToken: String,
) {
}