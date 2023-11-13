package com.quid.auth.authentication.user.gateway.web.request

data class LogInRequest(
    val username: String,
    val password: String
)