package com.quid.auth.token.gateway.web.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)