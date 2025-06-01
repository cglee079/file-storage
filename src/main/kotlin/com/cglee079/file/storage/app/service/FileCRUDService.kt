package com.cglee079.file.storage.app.service

import com.cglee079.file.storage.app.dto.*
import com.cglee079.file.storage.util.PathUtil.getParentPath
import com.cglee079.file.storage.util.PathUtil.mergePath
import org.apache.tomcat.util.http.fileupload.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files

@Service
class FileCRUDService(
    @Value("\${app.storage.directory:}") private val storageDirectory: String,
) {

    fun getFile(request: FileGet): File {
        val filePath = storageDirectory.mergePath(request.namespace).mergePath(request.path)
        return File(filePath)
    }

    fun copyFile(request: FileCopy): String {
        copyFile(request.namespace, request.sourcePath, request.destPath)
        return request.destPath
    }

    fun moveFile(request: FileMove): String {
        val (sourceFile, _) = copyFile(request.namespace, request.sourcePath, request.destPath)
        sourceFile.delete()
        return request.destPath
    }

    private fun copyFile(namespace: String, sourcePath: String, destPath: String): Pair<File, File> {
        val rootDirectoryPath = storageDirectory.mergePath(namespace)

        val destDirectoryPath = destPath.getParentPath()

        val sourceFile = File(rootDirectoryPath.mergePath(sourcePath))
        val destDirectory = File(rootDirectoryPath.mergePath(destDirectoryPath))
        FileUtils.forceMkdir(destDirectory)

        val destFile = File(rootDirectoryPath.mergePath(destPath))
        Files.copy(sourceFile.toPath(), destFile.toPath())

        return sourceFile to destFile
    }

    fun writeFile(write: FileWrite): String {
        val namespace = write.namespace
        val rootDirectoryPath = storageDirectory.mergePath(namespace)
        val filePath = write.path

        val directoryPath = filePath.getParentPath()

        val destDirectory = File(rootDirectoryPath.mergePath(directoryPath))
        FileUtils.forceMkdir(destDirectory)

        val destFile = File(rootDirectoryPath.mergePath(filePath))
        write.file.transferTo(destFile)

        return filePath
    }

    fun deleteFile(delete: FileDelete) {
        val namespace = delete.namespace
        val rootDirectoryPath = storageDirectory.mergePath(namespace)
        File(rootDirectoryPath.mergePath(delete.path).mergePath(delete.filename)).delete()
    }

}

