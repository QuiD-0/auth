package com.quid.auth.global.token.domain

import java.time.LocalDateTime

sealed interface Token {
    val header: Header
    val payload: Payload
    fun isExpired(): Boolean = payload.exp.isBefore(LocalDateTime.now())
    fun isNotExpired(): Boolean = !payload.exp.isBefore(LocalDateTime.now())
    val username: String
        get() = payload.username
}

data class AccessToken(
    override val header: Header = Header(),
    override val payload: Payload,
) : Token {

    constructor(username: String) : this(
        payload = Payload.accessType(username)
    )
}

data class RefreshToken(
    override val header: Header = Header(),
    override val payload: Payload,
) : Token {

    constructor() : this(
        payload = Payload.refreshType()
    )
}

