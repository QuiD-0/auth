package com.quid.auth.user.domain

import java.time.LocalDateTime

data class UserAuthority(
    val authoritySeq: Long? = null,
    val authorityName: AuthorityType,
    val regDate: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false,
) {
}