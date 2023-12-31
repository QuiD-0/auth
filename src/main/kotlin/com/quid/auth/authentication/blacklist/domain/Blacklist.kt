package com.quid.auth.authentication.blacklist.domain

import java.time.LocalDateTime

data class Blacklist(
    val blacklistSeq: Long? = null,
    val userSeq: Long,
    val description: String,
    val expireDate: LocalDateTime,
    val regDate: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false,
) {
    constructor(userSeq: Long, description: String, expireDate: LocalDateTime) : this(
        null,
        userSeq,
        description,
        expireDate,
    )

    fun delete() = copy(deleted = true)

    fun isActive(): Boolean = expireDate.isAfter(LocalDateTime.now())
}