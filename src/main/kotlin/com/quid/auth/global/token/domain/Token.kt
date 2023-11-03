package com.quid.auth.global.token.domain

import java.time.LocalDateTime

sealed interface Token {
    val header: Header
    val payload: Payload
    fun isExpired(): Boolean = payload.exp.isBefore(LocalDateTime.now())
    val username: String
        get() = payload.username
}

data class AccessToken(
    override val header: Header = Header(),
    override val payload: Payload,
) : Token {

    constructor(username: String, exp: LocalDateTime = LocalDateTime.now().plusMinutes(30)) : this(
        payload = Payload(
            sub = TokenType.ACCESS,
            exp = exp,
            username = username
        )
    )
}

data class RefreshToken(
    override val header: Header = Header(),
    override val payload: Payload,
) : Token {

    constructor(exp: LocalDateTime = LocalDateTime.now().plusDays(7)) : this(
        payload = Payload(
            sub = TokenType.REFRESH,
            exp = exp,
            username = ""
        )
    )
}

