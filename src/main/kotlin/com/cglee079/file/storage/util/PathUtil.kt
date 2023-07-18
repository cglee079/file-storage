package com.cglee079.file.storage.util

object PathUtil {

    fun String.mergePath(b: String): String {
        return if (this.endsWith("/") && b.startsWith("/"))
            this + b.substring(0, b.length)
        else if (!this.endsWith("/") && !b.startsWith("/")) {
            "$this/$b"
        } else {
            this + b
        }
    }

    fun String.getParentPath(): String {
        return this.split("/").toMutableList().dropLast(1).joinToString("/")
    }

}
