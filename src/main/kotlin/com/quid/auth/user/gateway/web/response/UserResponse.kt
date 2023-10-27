package com.quid.auth.user.gateway.web.response

import com.quid.auth.user.domain.User

data class UserResponse(
    val userSeq: Long,
    val name: String,
    val username: String,
)

fun UserResponse(user: () -> User): UserResponse =
    with(user()) {
        UserResponse(
            userSeq!!,
            name,
            username,
        )
    }