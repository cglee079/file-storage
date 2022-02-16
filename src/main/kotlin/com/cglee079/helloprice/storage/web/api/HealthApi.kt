package com.cglee079.helloprice.storage.web.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthApi {

    @GetMapping("/")
    fun home(): String {
        return "OK"
    }

}
