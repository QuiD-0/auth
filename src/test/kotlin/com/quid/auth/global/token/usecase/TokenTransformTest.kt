package com.quid.auth.global.token.usecase

import com.quid.auth.global.token.domain.Payload
import com.quid.auth.global.token.domain.Token
import com.quid.auth.global.token.domain.TokenType
import com.quid.auth.global.token.domain.TokenType.ACCESS
import com.quid.auth.global.token.usecase.TokenDecoder.*
import com.quid.auth.global.token.usecase.TokenEncoder.JwtTokenEncoder
import io.jsonwebtoken.security.SignatureException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class TokenTransformTest {
    private lateinit var tokenEncoder: TokenEncoder
    private lateinit var tokenDecoder: TokenDecoder

    private val token = Token(
        Payload(
            sub = ACCESS,
            exp = LocalDateTime.now().plusHours(1),
            username = "user"
        )
    )

    @Test
    @DisplayName("JWT토큰 인코딩")
    fun encodeToken() {
        tokenEncoder = JwtTokenEncoder("this_is_test_secret_code_for_token_provider")
        assertDoesNotThrow {
            tokenEncoder(token)
        }
    }

    @Test
    @DisplayName("JWT토큰 디코딩")
    fun decodeToken(){
        tokenDecoder = JwtTokenDecoder("this_is_test_secret_code_for_token_provider")

        assertDoesNotThrow {
            tokenDecoder(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                        "eyJzdWIiOiJhY2Nlc3NUb2tlbiIsImlzcyI6IlFVSURfTEFCIiwiZXhw" +
                        "IjozMzIyNDI5MzMyOCwiaWF0IjoxNjk4OTIwNTI4LCJqdGkiOiJmZjdk" +
                        "OTRmNC1hYjI2LTQzMzctYmRmYy1hZTljYjAzOTBkNjAiLCJ1c2VybmFt" +
                        "ZSI6InVzZXIifQ.PWGo895OTBn7BD-FJGao30xQSEvsMuOFp437_fo_I4w"
            )
        }
    }

    @Test
    @DisplayName("secret이 다를경우 에러 발생")
    fun decodeError(){
        tokenEncoder = JwtTokenEncoder("this_is_test_secret_code_for_token_provider")

        val token = tokenEncoder(token)

        tokenDecoder = JwtTokenDecoder("this_is_not_match_secret_code_for_token_provider")

        assertThrows<SignatureException> {
            tokenDecoder(token)
        }
    }

}