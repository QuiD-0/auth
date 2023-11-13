package com.quid.auth.authentication.user.gateway.repository.redis

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.quid.auth.authentication.user.domain.AuthType
import com.quid.auth.authentication.user.domain.UserAuthority
import java.time.LocalDateTime
import java.time.ZoneOffset

data class UserAuthorityRedisHash @JsonCreator constructor(
    @JsonProperty("authoritySeq")
    val authoritySeq: Long,
    @JsonProperty("userSeq")
    val userSeq: Long,
    @JsonProperty("authorityName")
    val authorityName: AuthType,
    @JsonProperty("regDate")
    val regDate: Long,
    @JsonProperty("deleted")
    val deleted: Boolean,
){
    constructor(userAuthority: UserAuthority) : this(
        userAuthority.authoritySeq!!,
        userAuthority.userSeq,
        userAuthority.authorityName,
        userAuthority.regDate.toEpochSecond(ZoneOffset.UTC),
        userAuthority.deleted,
    )

    fun toDomain() =
        UserAuthority(
            authoritySeq,
            userSeq,
            authorityName,
            LocalDateTime.ofEpochSecond(regDate, 0, ZoneOffset.UTC),
            deleted,
        )
}