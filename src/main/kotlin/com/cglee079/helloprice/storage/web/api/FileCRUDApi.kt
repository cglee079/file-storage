package com.cglee079.helloprice.storage.web.api

import com.cglee079.helloprice.storage.app.service.FileCRUDService
import com.cglee079.helloprice.storage.web.dto.request.FileWrite
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FileCRUDApi(
    private val fileCRUDService: FileCRUDService
) {
    @PostMapping("/api/write")
    fun upload(@ModelAttribute write: FileWrite): String {
        return fileCRUDService.writeFile(write)
    }
}
