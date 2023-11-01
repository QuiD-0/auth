package com.quid.auth.global.token.domain

data class Signature(
    val secret: String
) {
    init {
        require(secret.toByteArray().size >= 32) { "토큰 시크릿은 32바이트 이상이어야 합니다." }
    }
}