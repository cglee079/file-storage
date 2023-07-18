package com.cglee079.file.storage.app.service

import com.cglee079.file.storage.app.dto.FileDelete
import com.cglee079.file.storage.app.dto.FileMove
import com.cglee079.file.storage.app.dto.FileWrite
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

    fun moveFile(request: FileMove): String {
        val namespace = request.namespace
        val rootDirectoryPath = storageDirectory.mergePath(namespace)
        val sourcePath = request.sourcePath
        val destPath = request.destPath

        val destDirectoryPath = destPath.getParentPath()

        val sourceFile = File(rootDirectoryPath.mergePath(sourcePath))
        FileCRUDUtil.writeForceDirectory(rootDirectoryPath.mergePath(destDirectoryPath))
        FileCRUDUtil.copyFile(sourceFile, rootDirectoryPath.mergePath(destPath))

        FileCRUDUtil.deleteFile(sourceFile)

        return destPath
    }

    fun writeFile(write: FileWrite): String {
        val namespace = write.namespace
        val rootDirectoryPath = storageDirectory.mergePath(namespace)
        val filePath = write.path

        val directoryPath = filePath.getParentPath()

        FileCRUDUtil.writeForceDirectory(rootDirectoryPath.mergePath(directoryPath))
        FileCRUDUtil.writeFile(rootDirectoryPath.mergePath(filePath), write.file)

        return filePath
    }

    fun deleteFile(delete: FileDelete) {
        val namespace = delete.namespace
        val rootDirectoryPath = storageDirectory.mergePath(namespace)
        FileCRUDUtil.deleteFile(rootDirectoryPath.mergePath(delete.path), delete.filename)
    }


}


object FileCRUDUtil {

    fun copyFile(sourceFile: File, destPath: String): File {
        val destFile = File(destPath)
        Files.copy(sourceFile.toPath(), destFile.toPath())
        return destFile
    }

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

    fun deleteFile(directory: String, filename: String) {
        File(directory, filename).delete()
    }

    fun deleteFile(file: File) {
        file.delete()
    }

}
