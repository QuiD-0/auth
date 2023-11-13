package com.quid.auth.authentication.blacklist.gateway.repository.redis

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.quid.auth.authentication.blacklist.domain.Blacklist
import java.time.LocalDateTime
import java.time.ZoneOffset

class BlacklistRedisHash @JsonCreator constructor(
    @JsonProperty("blacklistSeq")
    val blacklistSeq: Long,
    @JsonProperty("userSeq")
    val userSeq: Long,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("expireDate")
    val expireDate: Long,
    @JsonProperty("regDate")
    val regDate: Long,
    @JsonProperty("deleted")
    val deleted: Boolean,
) {
    constructor(blacklist: Blacklist) : this(
        blacklist.blacklistSeq!!,
        blacklist.userSeq,
        blacklist.description,
        blacklist.expireDate.toEpochSecond(ZoneOffset.UTC),
        blacklist.regDate.toEpochSecond(ZoneOffset.UTC),
        blacklist.deleted,
    )

    fun toDomain(): Blacklist = Blacklist(
        blacklistSeq,
        userSeq,
        description,
        LocalDateTime.ofEpochSecond(expireDate, 0, ZoneOffset.UTC),
        LocalDateTime.ofEpochSecond(regDate, 0, ZoneOffset.UTC),
        deleted,
    )
}