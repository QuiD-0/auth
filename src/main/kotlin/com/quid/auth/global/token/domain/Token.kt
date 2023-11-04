package com.quid.auth.global.token.domain

import java.time.LocalDateTime

sealed interface Token {
    val header: Header
    val payload: Payload
    val username: String
        get() = payload.username
    fun isExpired(): Boolean = payload.exp.isBefore(LocalDateTime.now())
    fun isNotExpired(): Boolean = !payload.exp.isBefore(LocalDateTime.now())
}

data class AccessToken(
    override val header: Header,
    override val payload: Payload,
) : Token {
    constructor(payload: Payload) : this(
        Header.default(),
        payload
    )
    constructor(username: String) : this(
        Header.default(),
        Payload.accessType(username)
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

