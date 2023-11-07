package com.quid.auth.user.gateway.web.request

data class RevokeAuthorityRequest(
    val userAuthoritySeq: Long,
)