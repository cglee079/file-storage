package com.cglee079.file.storage.web.api

import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct
import javax.servlet.MultipartConfigElement

@RestController
class HealthApi(
        val multipartConfigElement: MultipartConfigElement,
){
    @PostConstruct
    fun init(){
        println("multipartConfigElement.maxFileSize : ${multipartConfigElement.maxFileSize}")
        println("multipartConfigElement.maxRequestSize : ${multipartConfigElement.maxRequestSize}")
    }
    @GetMapping("/")
    fun home(): String {
        return "OK"
    }

}
