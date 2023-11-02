package com.quid.auth.global.token.domain

data class Token(
    val header: Header = Header(),
    val payload: Payload,
) {
    constructor(payload: Payload) : this(Header(), payload)
}


