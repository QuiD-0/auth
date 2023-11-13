package com.quid.auth.userInfo.blacklist.domain

import java.time.LocalDateTime

data class Blacklist(
    val blacklistSeq: Long? = null,
    val userSeq: Long,
    val description: String,
    val expireDate: LocalDateTime = LocalDateTime.parse("9999-12-31T23:59:59"),
    val regDate: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false,
) {
    constructor(userSeq: Long, description: String) : this(
        null,
        userSeq,
        description,
    )

    constructor(userSeq: Long, description: String, expireDate: LocalDateTime) : this(
        null,
        userSeq,
        description,
        expireDate,
    )

    fun delete() = copy(deleted = true)

    fun isExpired(): Boolean = LocalDateTime.now().isAfter(expireDate)
    fun isNotExpired(): Boolean = LocalDateTime.now().isBefore(expireDate)
}