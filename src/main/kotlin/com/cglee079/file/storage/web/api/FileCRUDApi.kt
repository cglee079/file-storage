package com.cglee079.file.storage.web.api

import com.cglee079.file.storage.app.dto.FileCopy
import com.cglee079.file.storage.app.dto.FileDelete
import com.cglee079.file.storage.app.dto.FileMove
import com.cglee079.file.storage.app.dto.FileWrite
import com.cglee079.file.storage.app.service.FileCRUDService
import org.springframework.web.bind.annotation.*

@RestController
class FileCRUDApi(
    private val fileCRUDService: FileCRUDService
) {

    @PostMapping("/api/copy")
    fun copy(@RequestBody request: FileCopy): String {
        return fileCRUDService.copyFile(request)
    }

    @PostMapping("/api/move")
    fun move(@RequestBody request: FileMove): String {
        return fileCRUDService.moveFile(request)
    }

    @PostMapping("/api/write")
    fun upload(@ModelAttribute write: FileWrite): String {
        return fileCRUDService.writeFile(write)
    }

    @PostMapping("/api/delete")
    fun delete(@RequestBody delete: FileDelete) {
        fileCRUDService.deleteFile(delete)
    }
}
