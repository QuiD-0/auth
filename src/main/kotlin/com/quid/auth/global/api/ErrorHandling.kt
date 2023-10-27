package com.quid.auth.global.api

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandling {

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<Error<String>> =
        ResponseEntity<Error<String>>(
            Error(ex.message ?: "Unknown Error"),
            determineStatusCode(ex)
        ).also { ex.printStackTrace() }

    private fun determineStatusCode(ex: Exception): HttpStatus =
        when (ex) {
            is IllegalArgumentException -> BAD_REQUEST
            is NoSuchElementException -> NOT_FOUND
            is IllegalStateException -> CONFLICT
            is RuntimeException -> INTERNAL_SERVER_ERROR
            else -> INTERNAL_SERVER_ERROR
        }

}