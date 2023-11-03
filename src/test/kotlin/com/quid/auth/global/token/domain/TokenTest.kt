package com.quid.auth.global.token.domain

import com.quid.auth.global.token.domain.TokenType.ACCESS
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class TokenTest {

    @Test
    @DisplayName("토큰 생성")
    fun makeToken() {
        assertDoesNotThrow {
            AccessToken(
                exp = LocalDateTime.now().plusHours(1),
                username = "user"
            )
        }
    }

    @Test
    @DisplayName("토큰 시크릿은 32바이트 이상이어야 한다.")
    fun tokenSecretError() {
        assertThrows<IllegalArgumentException> {
            AccessToken(
                exp = LocalDateTime.now().plusDays(1),
                username = "user"
            )
        }
    }

}