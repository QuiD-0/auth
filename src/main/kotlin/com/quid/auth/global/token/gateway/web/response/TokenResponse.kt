package com.quid.auth.global.token.gateway.web.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)