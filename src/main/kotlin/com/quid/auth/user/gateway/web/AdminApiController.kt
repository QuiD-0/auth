package com.quid.auth.user.gateway.web

import com.quid.auth.user.domain.UserDetail
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminApiController {

    @PostMapping
    fun getUsers(@AuthenticationPrincipal user: UserDetail) =
        "${user.name} is admin"
}