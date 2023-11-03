package com.quid.auth.global.token.usecase

import com.quid.auth.global.token.domain.AccessToken
import com.quid.auth.global.token.domain.Token
import com.quid.auth.global.token.domain.TokenType.ACCESS
import com.quid.auth.global.token.usecase.TokenDecoder.JwtTokenDecoder
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

    private val token = AccessToken(
        exp = LocalDateTime.now().plusYears(999),
        username = "test"
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
    fun decodeToken() {
        tokenDecoder = JwtTokenDecoder("this_is_test_secret_code_for_token_provider")

        assertDoesNotThrow {
            tokenDecoder(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpc3MiOiJR" +
                        "VUlEX0xBQiIsImV4cCI6MzMyMjQzODY4NzMsImlhdCI6MTY5OTAxNDA3MywianRpIjo" +
                        "iZmViY2FjNmQtYmQ4OC00MmJkLThkM2QtN2RmM2RkY2Y4ZjY1IiwidXNlcm5hbWUiOi" +
                        "J0ZXN0In0.qTLvwxmlShfqkWw-g6UeMz4cW8nEftuac7nLTk3dWhE"
            )
        }
    }

    @Test
    @DisplayName("secret이 다를경우 에러 발생")
    fun decodeError() {
        tokenEncoder = JwtTokenEncoder("this_is_test_secret_code_for_token_provider")

        val token = tokenEncoder(token)

        tokenDecoder = JwtTokenDecoder("this_is_not_match_secret_code_for_token_provider")

        assertThrows<SignatureException> {
            tokenDecoder(token)
        }
    }

}