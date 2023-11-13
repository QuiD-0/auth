package com.quid.auth.userInfo.domain

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class UserTest {

    @Test
    @DisplayName("정상적인 유저 생성")
    fun create() {
        assertDoesNotThrow {
            User(
                username = "test",
                password = "password",
                email = "email@mail.com",
                name = "name",
            )
        }
    }

    @Test
    @DisplayName("이메일 형식이 아닌 경우 예외 발생")
    fun validation() {
        assertThrows<IllegalArgumentException> {
            User(
                username = "test",
                password = "password",
                email = "email",
                name = "name",
            )
        }
    }

    @Test
    @DisplayName("ID가 4자 이하인 경우 예외 발생")
    fun validation2() {
        assertThrows<IllegalArgumentException> {
            User(
                username = "tes",
                password = "password",
                email = "email",
                name = "name",
            )
        }
    }
}