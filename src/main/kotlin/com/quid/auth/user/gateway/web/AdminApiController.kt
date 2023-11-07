package com.quid.auth.user.gateway.web

import com.quid.auth.user.gateway.web.response.UserDetailResponse
import com.quid.auth.user.usecase.FindAdmin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminApiController(
    private val findAdmin: FindAdmin
) {

    @PostMapping
    fun getAdminUsers(): List<UserDetailResponse> = findAdmin.adminList()
        .map { UserDetailResponse(it.name, it.username) }

}