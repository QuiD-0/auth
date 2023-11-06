package com.quid.auth.token.domain

data class Header(
    val typ: String,
    val alg: String,
) {
    val value: Map<String, String>
        get() = mapOf("typ" to typ, "alg" to alg)

    companion object{
        fun default() = Header("JWT", "HS256")
    }
}