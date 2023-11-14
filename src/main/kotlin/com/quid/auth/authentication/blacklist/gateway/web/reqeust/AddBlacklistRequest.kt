package com.quid.auth.authentication.blacklist.gateway.web.reqeust

import com.quid.auth.authentication.blacklist.domain.Blacklist
import java.time.LocalDateTime

data class AddBlacklistRequest(
    val userSeq: Long,
    val reason: String,
    val expiredDate: LocalDateTime?
) {
    fun toBlacklist(): Blacklist =
        Blacklist(
            userSeq = userSeq,
            description = reason,
            expireDate = expiredDate ?: LocalDateTime.parse("9999-12-31T23:59:59")
        )
}
