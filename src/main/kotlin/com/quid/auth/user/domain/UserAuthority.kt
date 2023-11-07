package com.quid.auth.user.domain

import java.time.LocalDateTime

data class UserAuthority(
    val authoritySeq: Long? = null,
    val userSeq: Long,
    val authorityName: AuthType,
    val regDate: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false,
) {
}