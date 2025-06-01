package com.cglee079.file.storage.web.api

import com.cglee079.file.storage.app.dto.*
import com.cglee079.file.storage.app.service.FileCRUDService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


@RestController
class FileCRUDApi(
    private val fileCRUDService: FileCRUDService
) {

    @PostMapping("/api/get")
    fun get(@RequestBody request: FileGet): ResponseEntity<out Any> {
        val file = fileCRUDService.getFile(request)

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found")
        }

        try {
            val inputStream: InputStream = FileInputStream(file)
            val resource = InputStreamResource(inputStream)

            // 파일 확장자별로 Content-Type을 바꿔주는 것도 좋음
            val headers: HttpHeaders = HttpHeaders().apply {
                val filename = file.name.lowercase()
                this.contentType = when {
                    filename.endsWith(".jpg") || filename.endsWith(".jpeg") -> MediaType.IMAGE_JPEG
                    filename.endsWith(".png") -> MediaType.IMAGE_PNG
                    filename.endsWith(".gif") -> MediaType.IMAGE_GIF
                    else -> MediaType.APPLICATION_OCTET_STREAM
                }
            }


            return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body<InputStreamResource>(resource)
        } catch (e: IOException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body<String>("Error reading file")
        }
    }

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
