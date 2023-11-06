package com.quid.auth.token.gateway.repository.model

data class UserTokenJti(
    val username: String,
    val refreshTokenJti: String,
) {
}