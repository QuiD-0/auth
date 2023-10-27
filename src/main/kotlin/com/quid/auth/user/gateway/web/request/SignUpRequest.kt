package com.quid.auth.user.gateway.web.request

import com.quid.auth.user.domain.UserInfo

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String,
    val name: String,
) {

    fun toUserInfo() = UserInfo(
        username = username,
        password = password,
        email = email,
        name = name,
    )
}