package com.quid.auth.global.log

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.CommonsRequestLoggingFilter

@Configuration
class RequestLoggingFilter {

    @Bean
    fun commonsRequestLoggingFilter(): CommonsRequestLoggingFilter {
        return CommonsRequestLoggingFilter().apply {
            setIncludePayload(true)
            setIncludeHeaders(false)
            setIncludeClientInfo(true)
            setIncludeQueryString(false)
            setMaxPayloadLength(10000)
            setAfterMessagePrefix("REQUEST DATA : ")
        }
    }
}