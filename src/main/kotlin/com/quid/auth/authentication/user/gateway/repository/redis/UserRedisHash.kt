package com.quid.auth.authentication.user.gateway.repository.redis

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.quid.auth.authentication.user.domain.User

data class UserRedisHash @JsonCreator constructor(
    @JsonProperty("userSeq") val userSeq: Long,
    @JsonProperty("username") val username: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("deleted") val deleted: Boolean,
) {
    constructor(user: User) : this(
        user.userSeq!!,
        user.username,
        user.password,
        user.email,
        user.name,
        user.deleted,
    )

    fun toDomain() = User(
        userSeq,
        username,
        password,
        email,
        name,
        deleted,
    )
}
