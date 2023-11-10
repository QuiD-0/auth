package com.quid.auth.global.log

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class LoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val log: Logger = LoggerFactory.getLogger(this::class.java)

        val cachingRequestWrapper = ContentCachingRequestWrapper(request)
        val cachingResponseWrapper = ContentCachingResponseWrapper(response)

        val startTime = System.currentTimeMillis()
        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper)
        val end = System.currentTimeMillis()

        try {
            createInstance(
                requestWrapper = cachingRequestWrapper,
                responseWrapper = cachingResponseWrapper,
                elapsedTime = (end - startTime) / 1000.0
            ).let { log.info(it.prettier()) }

            cachingResponseWrapper.copyBodyToResponse()
        } catch (e: Exception) {
            log.error("LoggingFilter error : ${e.message}")
        }
    }
}