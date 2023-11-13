package com.quid.auth.userInfo.user.gateway.web.request

import com.quid.auth.userInfo.user.domain.User

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String,
    val name: String,
) {

    fun toUser() = User(
        username = username,
        password = password,
        email = email,
        name = name,
    )
}