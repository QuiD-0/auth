package com.quid.auth.user.gateway.web.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)