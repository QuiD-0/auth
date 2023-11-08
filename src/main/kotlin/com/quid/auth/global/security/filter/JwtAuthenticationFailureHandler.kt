package com.quid.auth.global.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.quid.auth.global.api.Error
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFailureHandler: AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        sendError(response, accessDeniedException)
    }

    private fun sendError(
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.apply {
            status = 403
            contentType = "application/json"
            writer.write(ObjectMapper().writeValueAsString(
                Error { accessDeniedException?.message ?: "Access Denied" }
            ))
        }
    }

}