package com.quid.auth.token.domain

import java.time.LocalDateTime

sealed interface Token {
    val header: Header
    val payload: Payload
    val username: String
        get() = payload.username
    fun isExpired(): Boolean = payload.exp.isBefore(LocalDateTime.now())
    fun isNotExpired(): Boolean = !payload.exp.isBefore(LocalDateTime.now())

    val id: String
        get() = payload.jti
}

data class AccessToken(
    override val header: Header,
    override val payload: Payload,
) : Token {
    constructor(payload: Payload, header: Header = Header.default()) : this(
        header,
        payload
    )
}

data class RefreshToken(
    override val header: Header,
    override val payload: Payload,
) : Token {

    constructor() : this(
        Header.default(),
        Payload.refreshType()
    )
}

