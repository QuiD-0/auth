package com.quid.auth.user.gateway.web

import com.quid.auth.user.gateway.web.request.GrantAuthorityRequest
import com.quid.auth.user.gateway.web.request.RevokeAuthorityRequest
import com.quid.auth.user.gateway.web.response.UserDetailResponse
import com.quid.auth.user.usecase.FindAdmin
import com.quid.auth.user.usecase.GrantAuthority
import com.quid.auth.user.usecase.RevokeAuthority
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminApiController(
    private val findAdmin: FindAdmin,
    private val grantAuthority: GrantAuthority,
    private val revokeAuthority: RevokeAuthority
) {

    @PostMapping
    fun getAdminUsers(): List<UserDetailResponse> = findAdmin.adminList()
        .map { UserDetailResponse(it.name, it.username) }

    @PostMapping("/grant")
    fun grantAuthority(@RequestBody request: GrantAuthorityRequest) =
        request.toDomain()
            .run { grantAuthority(this) }

    @PostMapping("/revoke")
    fun revokeAuthority(@RequestBody request: RevokeAuthorityRequest) =
        revokeAuthority(request.userAuthoritySeq)

}