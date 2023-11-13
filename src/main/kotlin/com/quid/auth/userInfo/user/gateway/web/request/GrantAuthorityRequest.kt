package com.quid.auth.userInfo.user.gateway.web.request

import com.quid.auth.userInfo.user.domain.UserAuthority

data class GrantAuthorityRequest(
    val userSeq: Long,
    val authority: String,
){
    fun toDomain()= UserAuthority(
        userSeq = userSeq,
        authorityName = com.quid.auth.userInfo.user.domain.AuthType.of(authority)
    )
}
