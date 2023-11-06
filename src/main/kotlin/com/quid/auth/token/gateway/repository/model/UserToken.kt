package com.quid.auth.token.gateway.repository.model

data class UserToken(
    val username: String,
    val refreshToken: String,
) {
}