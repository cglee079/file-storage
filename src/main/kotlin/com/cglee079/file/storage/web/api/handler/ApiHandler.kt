package com.cglee079.file.storage.web.api.handler

import com.cglee079.file.storage.context.ThreadLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ApiHandler {

    @ExceptionHandler(Exception::class)
    fun handleUnknownException(ex: Exception): ResponseEntity<String> {
        ex.printStackTrace()
        ThreadLogger.addException(ex)
        return status(HttpStatus.INTERNAL_SERVER_ERROR).body("죄송합니다, 알 수 없는 에러가 발생했습니다")
    }


}
