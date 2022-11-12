package com.cglee079.file.storage.app.service

import com.cglee079.file.storage.app.dto.FileDelete
import com.cglee079.file.storage.app.dto.FileWrite
import com.cglee079.file.storage.util.PathUtil.mergePath
import org.apache.tomcat.util.http.fileupload.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class FileCRUDService(
    @Value("\${app.storage.directory:}") private val storageDirectory: String,
) {

    fun writeFile(write: FileWrite): String {
        val namespace = write.namespace
        val rootDirectoryPath = storageDirectory.mergePath(namespace)
        val filePath = write.path

        val directoryPath = filePath.split("/").toMutableList().dropLast(1).joinToString("/")

        FileCRUDUtil.writeForceDirectory(rootDirectoryPath.mergePath(directoryPath))
        FileCRUDUtil.writeFile(rootDirectoryPath.mergePath(filePath), write.file)

        return namespace.mergePath(filePath)
    }
    fun deleteFile(delete: FileDelete) {
        val namespace = delete.namespace
        val rootDirectoryPath = storageDirectory.mergePath(namespace)
        FileCRUDUtil.deleteFile(rootDirectoryPath.mergePath(delete.path), delete.filename)
    }

}

object FileCRUDUtil {

    fun writeFile(path: String, multipartFile: MultipartFile): File {
        val file = File(path)
        multipartFile.transferTo(file) // 쓰기 연산
        return file
    }

    fun writeForceDirectory(path: String): File {
        val directory = File(path)
        FileUtils.forceMkdir(directory)
        return directory
    }

    fun deleteFile(path: String, filename: String) {
        File(path, filename).delete()
    }

}
