package com.quid.auth.token.gateway.web.request

data class RefreshTokenRequest(
    val accessToken: String,
    val refreshToken: String,
) {
}