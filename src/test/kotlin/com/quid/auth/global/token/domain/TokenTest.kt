package com.quid.auth.global.token.domain

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

}