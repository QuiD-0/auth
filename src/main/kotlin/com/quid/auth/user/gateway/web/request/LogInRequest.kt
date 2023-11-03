package com.quid.auth.user.gateway.web.request

data class LogInRequest(
    val username: String,
    val password: String
) {
}