package com.quid.auth.user.gateway.web.response

import com.quid.auth.user.domain.User

data class UserResponse(
    val userSeq: Long,
    val username: String,
)

fun UserResponse(user: () -> User): UserResponse =
    UserResponse(
        user().userSeq!!,
        user().username,
    )