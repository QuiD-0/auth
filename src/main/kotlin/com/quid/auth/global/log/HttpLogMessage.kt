package com.quid.auth.global.log

import org.springframework.http.HttpStatus
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

data class HttpLogMessage(
    val httpMethod: String,
    val requestUri: String,
    val httpStatus: HttpStatus,
    val clientIp: String,
    val elapsedTime: Double,
    val requestBody: String?,
    val responseBody: String?,
) {
    fun prettier(): String {
        return """
            |
            |================== HTTP LOG ==================
            |HTTP METHOD : $httpMethod
            |REQUEST URI : $requestUri
            |HTTP STATUS : $httpStatus
            |ELAPSED TIME : $elapsedTime
            |REQUEST BODY : $requestBody
        """.trimMargin()
    }
}

fun createInstance(
    requestWrapper: ContentCachingRequestWrapper,
    responseWrapper: ContentCachingResponseWrapper,
    elapsedTime: Double
): HttpLogMessage = HttpLogMessage(
    httpMethod = requestWrapper.method,
    requestUri = requestWrapper.requestURI,
    httpStatus = HttpStatus.valueOf(responseWrapper.status),
    clientIp = requestWrapper.request.remoteAddr,
    elapsedTime = elapsedTime,
    requestBody = requestWrapper.contentAsByteArray.toString(Charsets.UTF_8),
    responseBody = responseWrapper.contentAsByteArray.toString(Charsets.UTF_8),
)