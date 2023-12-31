package com.quid.auth.authentication.user.domain

import java.time.LocalDateTime

data class UserAuthority(
    val authoritySeq: Long? = null,
    val userSeq: Long,
    val authorityName: AuthType,
    val regDate: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false,
) {
    constructor(userSeq: Long, authorityName: AuthType) : this(
        null,
        userSeq,
        authorityName,
    )

    fun delete() = copy(deleted = true)
}