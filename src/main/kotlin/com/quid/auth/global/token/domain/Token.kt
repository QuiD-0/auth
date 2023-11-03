package com.quid.auth.global.token.domain

import java.time.LocalDateTime

sealed interface Token {
    val payload: Payload
    val header: Header
        get() = Header()
    val username: String
        get() = payload.username
    fun isExpired(): Boolean = payload.exp.isBefore(LocalDateTime.now())
    fun isNotExpired(): Boolean = !payload.exp.isBefore(LocalDateTime.now())
}

data class AccessToken(
    override val payload: Payload,
) : Token {

    constructor(username: String) : this(
        Payload.accessType(username)
    )
}

data class RefreshToken(
    override val payload: Payload,
) : Token {

    constructor() : this(
        Payload.refreshType()
    )
}

