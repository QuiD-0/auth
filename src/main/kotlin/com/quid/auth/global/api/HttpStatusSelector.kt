package com.quid.auth.global.api

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus

interface HttpStatusSelector {
    operator fun invoke(ex: () -> Exception): HttpStatus

    class HttpStatusSelectorImpl : HttpStatusSelector {
        override fun invoke(ex: () -> Exception): HttpStatus =
            when (ex) {
                is IllegalArgumentException -> HttpStatus.BAD_REQUEST
                is NoSuchElementException -> HttpStatus.NOT_FOUND
                is IllegalStateException -> HttpStatus.CONFLICT
                is SignatureException -> HttpStatus.UNAUTHORIZED
                is ExpiredJwtException -> HttpStatus.UNAUTHORIZED
                is MalformedJwtException -> HttpStatus.UNAUTHORIZED
                else -> HttpStatus.INTERNAL_SERVER_ERROR
            }
    }

    companion object: HttpStatusSelector by HttpStatusSelectorImpl()

}