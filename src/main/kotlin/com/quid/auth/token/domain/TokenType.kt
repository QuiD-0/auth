package com.quid.auth.token.domain

enum class TokenType {
    ACCESS, REFRESH;

    companion object {
        fun of(subject: String): TokenType {
            return values().find { it.name == subject } ?: throw IllegalArgumentException("토큰 타입이 잘못되었습니다.")
        }
    }
}