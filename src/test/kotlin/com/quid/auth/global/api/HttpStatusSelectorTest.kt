package com.quid.auth.global.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class HttpStatusSelectorTest{

    @Test
    fun `IllegalArgumentException should return INTERNAL_SERVER_ERROR`(){
        val ex = IllegalArgumentException()
        val status = HttpStatusSelector { ex }
        assertEquals(status, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}