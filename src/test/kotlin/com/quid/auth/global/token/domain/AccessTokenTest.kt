package com.quid.auth.global.token.domain

import io.jsonwebtoken.security.WeakKeyException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class AccessTokenTest{

    @Test
    @DisplayName("토큰 생성")
    fun makeToken(){
        assertDoesNotThrow {
            AccessToken(
                payload = TokenPayload(
                    sub = "test",
                    exp = LocalDateTime.now().plusDays(1),
                ),
                signature = TokenSignature("testSecretLengthIsMoreThan32Bytes")
            ).encode()
        }
    }

    @Test
    @DisplayName("토큰 시크릿은 32바이트 이상이어야 한다.")
    fun tokenSecretError(){
        assertThrows<WeakKeyException> {
            AccessToken(
                payload = TokenPayload(
                    sub = "test",
                    exp = LocalDateTime.now().plusDays(1),
                ),
                signature = TokenSignature("testSecret")
            ).encode()
        }
    }

    @Test
    @DisplayName("토큰스트링으로 AccessToken 생성")
    fun decodeToken(){
        decodeToken(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaXNzIjoiUVVJRF9MQUIiLCJleHAiOjE2OTg5NDIxODksImlhdCI6MTY5ODg1NTc4OSwianRpIjoiZTFlMTUzOWUtMWE2MC00Y2VmLTg2MzgtZmVkYTFjZGJiMjQ2In0.DD7fzsDLTrxsSopjzjsfQpcTGVikmOg2b3IQAd42T9k",
            "testSecretLengthIsMoreThan32Bytes"
        ).also { println(it) }
    }
}