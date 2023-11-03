package com.quid.auth.global.token.domain

import java.time.LocalDateTime

data class Token(
    val header: Header = Header(),
    val payload: Payload,
) {
    constructor(payload: Payload) : this(Header(), payload)
    constructor(sub: TokenType, exp: LocalDateTime, username: String) : this(
        Payload(
            sub = sub,
            exp = exp,
            username = username
        )
    )
}


