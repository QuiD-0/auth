package com.quid.auth.userInfo.user.gateway.web.request

data class LogInRequest(
    val username: String,
    val password: String
)