package com.quid.auth.authentication.user.gateway.web.request

import com.quid.auth.authentication.user.domain.UserAuthority

data class GrantAuthorityRequest(
    val userSeq: Long,
    val authority: String,
){
    fun toDomain()= UserAuthority(
        userSeq = userSeq,
        authorityName = com.quid.auth.authentication.user.domain.AuthType.of(authority)
    )
}
