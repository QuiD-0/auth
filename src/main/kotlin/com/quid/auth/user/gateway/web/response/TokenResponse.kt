package com.quid.auth.user.gateway.web.response

import com.quid.auth.user.domain.User

@JvmInline
value class TokenResponse(
    val token: String
)