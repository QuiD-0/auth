package com.quid.auth.token.domain

import java.time.LocalDateTime

sealed interface Token {
    val header: Header
    val payload: Payload
    val username: String
        get() = payload.username
    fun isNotExpired(): Boolean = !payload.exp.isBefore(LocalDateTime.now())
    val id: String
        get() = payload.jti
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

