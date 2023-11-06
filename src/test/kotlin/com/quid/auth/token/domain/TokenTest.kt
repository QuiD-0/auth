package com.quid.auth.token.domain

import com.quid.auth.token.domain.AccessToken
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class TokenTest {

    @Test
    @DisplayName("토큰 생성")
    fun makeToken() {
        assertDoesNotThrow {
            AccessToken("user")
        }
    }

}