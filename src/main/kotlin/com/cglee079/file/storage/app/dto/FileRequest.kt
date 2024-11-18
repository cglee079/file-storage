package com.cglee079.file.storage.app.dto

import org.springframework.web.multipart.MultipartFile

data class FileMove(
    val namespace: String,
    val sourcePath: String,
    val destPath: String,
)

data class FileCopy(
    val namespace: String,
    val sourcePath: String,
    val destPath: String,
)

data class FileWrite(
    val namespace: String,
    val path: String,
    val file: MultipartFile,
)

data class FileDelete(
    val namespace: String,
    val path: String,
    val filename: String
)
