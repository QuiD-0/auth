package com.quid.auth.global.token.domain

import io.jsonwebtoken.security.SignatureException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class TokenTest{

    @Test
    @DisplayName("토큰 생성")
    fun makeToken(){
        assertDoesNotThrow {
            Token(
                Payload(
                    sub = "accessToken",
                    exp = LocalDateTime.now().plusHours(1),
                    username = "user"
                ),
                Signature("testSecretLengthIsMoreThan32Bytes")
            ).encode()
        }
    }

    @Test
    @DisplayName("토큰 시크릿은 32바이트 이상이어야 한다.")
    fun tokenSecretError(){
        assertThrows<IllegalArgumentException> {
            Token(
                Payload(
                    sub = "accessToken",
                    exp = LocalDateTime.now().plusDays(1),
                    username = "user"
                ),
                Signature("testSecret")
            ).encode()
        }
    }

    @Test
    @DisplayName("비밀키가 틀리면 에러 발생")
    fun tokenDecodeFail(){
        assertThrows<SignatureException> {
            decodeToken(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaXNzIjoiUVVJRF9MQUIiLCJleHAiOjMxNzIzNjg0NDc3OSwiaWF0IjoxNjk4ODYwNzc5LCJqdGkiOiIzNjFlYmQ4Mi0xMWJjLTQ4ZjktODJkZS0zNWUwZWVjOGQwZTIiLCJ1c2VybmFtZSI6InVzZXIifQ.vzjLXUBO_PdCwNc_5qUgWqrfTb7n2S9nuvUq7BOQCJQ",
                "itsNotSecretKeyitsNotSecretKeyitsNotSecretKey"
            ).also { println(it) }
        }
    }

    @Test
    @DisplayName("토큰스트링으로 AccessToken 생성")
    fun decodeToken(){
        assertDoesNotThrow {
            decodeToken(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaXNzIjoiUVVJRF9MQUIiLCJleHAiOjMxNzIzNjg0NDc3OSwiaWF0IjoxNjk4ODYwNzc5LCJqdGkiOiIzNjFlYmQ4Mi0xMWJjLTQ4ZjktODJkZS0zNWUwZWVjOGQwZTIiLCJ1c2VybmFtZSI6InVzZXIifQ.vzjLXUBO_PdCwNc_5qUgWqrfTb7n2S9nuvUq7BOQCJQ",
                "testSecretLengthIsMoreThan32Bytes"
            ).also { println(it) }
        }
    }
}