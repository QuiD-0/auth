package com.quid.auth.authentication.blacklist.gateway.web

import com.quid.auth.authentication.blacklist.gateway.web.reqeust.AddBlacklistRequest
import com.quid.auth.authentication.blacklist.usecase.BlackUser
import com.quid.auth.global.api.Success
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/blacklist")
class BlacklistApiController(
    private val blackUser: BlackUser
) {

    @PostMapping
    fun addBlacklist(@RequestBody request: AddBlacklistRequest) =
        Success { blackUser(request.toBlacklist()) }
}