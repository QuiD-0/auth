package com.quid.auth.blacklist.domain

import java.time.LocalDateTime

data class Blacklist(
    val blacklistSeq: Long? = null,
    val userSeq: Long,
    val regDate: LocalDateTime = LocalDateTime.now(),
    val expireDate: LocalDateTime = LocalDateTime.parse("9999-12-31T23:59:59"),
    val deleted: Boolean = false,
) {
    constructor(userSeq: Long) : this(
        null,
        userSeq,
    )

    constructor(userSeq: Long, expireDate: LocalDateTime) : this(
        null,
        userSeq,
        expireDate = expireDate,
    )

    fun delete() = copy(deleted = true)
}