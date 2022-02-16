package com.cglee079.helloprice.storage.app.service

import com.cglee079.helloprice.storage.util.PathUtil.mergePath
import com.cglee079.helloprice.storage.web.dto.request.FileWrite
import org.apache.tomcat.util.http.fileupload.FileUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class FileCRUDService(
    @Value("\${app.storage.directory:}")
    private val uploadedDirectory: String,

    @Value("\${static.url:}")
    private val staticUrl: String,
) {

    fun writeFile(write: FileWrite): String {
        val directoryPath = write.path.split("/").toMutableList().dropLast(1).joinToString("/")

        FileCRUDUtil.writeForceDirectory(uploadedDirectory.mergePath(directoryPath))
        FileCRUDUtil.writeFile(uploadedDirectory.mergePath(write.path), write.file)

        return staticUrl.mergePath(write.path)
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
}
