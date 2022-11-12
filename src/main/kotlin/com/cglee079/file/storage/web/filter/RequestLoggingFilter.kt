package com.cglee079.file.storage.web.filter

import com.cglee079.file.storage.context.Context
import com.cglee079.file.storage.context.ThreadLogger
import com.cglee079.file.storage.util.DateTimeUtil.toISO
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RequestLoggingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(originalRequest: HttpServletRequest, originalResponse: HttpServletResponse, filterChain: FilterChain) {
        val request = ContentCachingRequestWrapper(originalRequest)
        val response = ContentCachingResponseWrapper(originalResponse)
        val requestAt = LocalDateTime.now()

        ThreadLogger.init(Context.xid())

        try {
            filterChain.doFilter(request, response)
        } catch (ex: Exception) {
            ThreadLogger.addException(ex)
        } finally {
            addRequestLog(request, requestAt)
            addResponseLog(response, requestAt, LocalDateTime.now())

            if (request.requestURI != "/" && (request.getHeader("my") != "my")) {
                ThreadLogger.info("REQUEST")
            }

            response.copyBodyToResponse()
        }
    }

    private fun addRequestLog(request: ContentCachingRequestWrapper, requestAt: LocalDateTime) {
        ThreadLogger.add("app.request.url", request.requestURI)
        ThreadLogger.add("app.request.query-string", request.queryString)
        ThreadLogger.add("app.request.method", request.method)

        val headers = request.headerNames.toList()
            .associateWith { request.getHeaders(it).toList().toString() }
            .toMutableMap()

        ThreadLogger.add("app.request.headers", headers.toString())
        ThreadLogger.add("app.request.header.x-real-ip", headers["x-real-ip"])
        ThreadLogger.add("app.request.header.x-original-forwarded-for", headers["x-original-forwarded-for"])
        ThreadLogger.addAll("app.request.parameters", request.parameterMap.keys.associateWith { Arrays.toString(request.parameterMap[it]) })
        ThreadLogger.add("app.request.remote-addr", request.remoteAddr)
        ThreadLogger.add("app.request.body-size", request.contentLength.toString())
        ThreadLogger.add("app.request.at", requestAt.toISO())
    }

    private fun addResponseLog(response: ContentCachingResponseWrapper, requestAt: LocalDateTime, responseAt: LocalDateTime): Map<String, Any?> {
        val log = mutableMapOf<String, Any?>()

        val headers = response.headerNames.toList()
            .associateWith { response.getHeaders(it).toList().toString() }
            .toMutableMap()

        ThreadLogger.add("app.response.headers", headers.toString())
        ThreadLogger.add("app.response.status", response.status.toString())
        ThreadLogger.add("app.response.body-size", response.contentSize.toString())
        ThreadLogger.add("app.response.elapsed-time", ChronoUnit.MILLIS.between(requestAt, responseAt).toString())
        ThreadLogger.add("app.response.at", responseAt.toISO())

        return log
    }
}
