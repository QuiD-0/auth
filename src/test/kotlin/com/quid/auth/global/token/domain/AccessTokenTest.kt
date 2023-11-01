package com.quid.auth.global.token.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDateTime

class AccessTokenTest{

    @Test
    fun makeToken(){
        assertDoesNotThrow {
            AccessToken(
                sub = "test",
                exp = LocalDateTime.now().plusDays(1),
                secret = "testSecretLengthIsMoreThan32Bytes"
            ).toJWT()
        }
    }
}