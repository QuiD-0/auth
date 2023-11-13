package com.quid.auth.authentication.user.gateway.web

import com.quid.auth.authentication.user.domain.UserAuthority
import com.quid.auth.authentication.user.gateway.web.request.GrantAuthorityRequest
import com.quid.auth.authentication.user.gateway.web.request.RevokeAuthorityRequest
import com.quid.auth.authentication.user.gateway.web.response.UserDetailResponse
import com.quid.auth.authentication.user.usecase.FindAdmin
import com.quid.auth.authentication.user.usecase.GrantAuthority
import com.quid.auth.authentication.user.usecase.RevokeAuthority
import com.quid.auth.global.api.ApiResponse
import com.quid.auth.global.api.Success
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminApiController(
    private val findAdmin: FindAdmin,
    private val grantAuthority: GrantAuthority,
    private val revokeAuthority: RevokeAuthority
) {

    @GetMapping
    fun getAdminUsers(): ApiResponse<List<UserDetailResponse>> = findAdmin.adminList()
        .map { UserDetailResponse(it.name, it.username) }
        .let { Success { it } }

    @GetMapping("/{username}")
    fun getAdminUser(@PathVariable username: String): ApiResponse<List<UserAuthority>> =
        findAdmin.authorityList(username)
            .let { Success { it } }

    @PostMapping("/grant")
    fun grantUserAuthority(@RequestBody request: GrantAuthorityRequest): ApiResponse<String>  =
        request
            .run {
                grantAuthority(
                    this.userSeq,
                    com.quid.auth.authentication.user.domain.AuthType.of(this.authority)
                )
            }
            .let { Success { "성공적으로 추가되었습니다." } }


    @PostMapping("/revoke")
    fun revokeUserAuthority(@RequestBody request: RevokeAuthorityRequest): ApiResponse<String> =
        revokeAuthority(request.userAuthoritySeq)
            .run { Success { "성공적으로 삭제되었습니다." } }

}