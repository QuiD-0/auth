package com.quid.auth.global.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandling {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Error> =
        ResponseEntity<Error>(
            Error{ ex.message ?: "Unknown Error" },
            HttpStatusSelector { ex }
        ).also { ex.printStackTrace() }


}