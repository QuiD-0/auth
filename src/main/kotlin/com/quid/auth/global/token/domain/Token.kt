package com.quid.auth.global.token.domain

import java.time.LocalDateTime

sealed interface Token{
    val header: Header
    val payload: Payload
}

data class AccessToken(
    override val header: Header = Header(),
    override val payload: Payload,
):Token {
    constructor(exp: LocalDateTime, username: String) : this(
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
):Token {
    constructor(exp: LocalDateTime, username: String) : this(
        payload = Payload(
            sub = TokenType.REFRESH,
            exp = exp,
            username = username
        )
    )
}

