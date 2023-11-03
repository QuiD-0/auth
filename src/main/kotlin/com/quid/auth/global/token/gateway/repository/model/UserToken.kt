package com.quid.auth.global.token.gateway.repository.model

data class UserToken(
    val username: String,
    val refreshToken: String,
) {
}