package com.quid.auth.user.domain

import java.time.LocalDateTime

data class UserAuthority(
    val authoritySeq: Long? = null,
    val authorityLevel: Int,
    val authorityName: String,
    val regDate: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false,
) {
}