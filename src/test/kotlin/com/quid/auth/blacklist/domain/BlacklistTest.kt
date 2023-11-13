package com.quid.auth.blacklist.domain

import com.quid.auth.userInfo.blacklist.domain.Blacklist
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class BlacklistTest {

    @Test
    @DisplayName("Blacklist expireDate")
    fun expireDateTest() {
        val blacklist = Blacklist(1, "정지", LocalDateTime.now().plusDays(1))
        assertTrue(blacklist.isNotExpired())
    }
}