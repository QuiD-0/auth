package com.quid.auth.user.gateway.web.request

import com.quid.auth.user.domain.AuthType
import com.quid.auth.user.domain.UserAuthority

data class GrantAuthorityRequest(
    val userSeq: Long,
    val authority: AuthType,
){
    fun toDomain()= UserAuthority(
        userSeq = userSeq,
        authorityName = authority
    )
}
