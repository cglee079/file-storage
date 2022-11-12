package com.cglee079.file.storage.web.api

import com.cglee079.file.storage.app.dto.FileDelete
import com.cglee079.file.storage.app.dto.FileWrite
import com.cglee079.file.storage.app.service.FileCRUDService
import org.springframework.web.bind.annotation.*

@RestController
class FileCRUDApi(
    private val fileCRUDService: FileCRUDService
) {
    @PostMapping("/api/write")
    fun upload(@ModelAttribute write: FileWrite): String {
        return fileCRUDService.writeFile(write)
    }
    @DeleteMapping("/api/file")
    fun delete(@RequestBody delete: FileDelete) {
        fileCRUDService.deleteFile(delete)
    }
}
