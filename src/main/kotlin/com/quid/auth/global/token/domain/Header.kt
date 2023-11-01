package com.quid.auth.global.token.domain

data class Header(
    val typ: String = "JWT",
    val alg: String = "HS256"
) {
    val value: Map<String, String>
        get() = mapOf("typ" to typ, "alg" to alg)
}