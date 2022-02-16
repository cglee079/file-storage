package com.cglee079.helloprice.storage.web.dto.request

import org.springframework.web.multipart.MultipartFile

data class FileWrite(
    val path: String,
    val file: MultipartFile,
)
