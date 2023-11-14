package com.quid.auth.authentication.blacklist.gateway.web.reqeust

import com.quid.auth.authentication.blacklist.domain.Blacklist
import java.time.LocalDateTime

data class AddBlacklistRequest(
    val userSeq: Long,
    val reason: String,
    val expiredDays: Long
) {
    fun toBlacklist(): Blacklist =
        Blacklist(
            userSeq = userSeq,
            description = reason,
            expireDate = getExpiredDate(expiredDays)
        )

    private fun getExpiredDate(expiredDays: Long): LocalDateTime {
        if (expiredDays == 0L) return LocalDateTime.parse("9999-12-31T23:59:59")
        return LocalDateTime.now().plusDays(expiredDays)
    }
}
