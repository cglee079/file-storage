package com.cglee079.helloprice.storage.util

object PathUtil {

    fun String.mergePath(b: String): String {
        return if (b.startsWith("/")) {
            this + b
        } else "$this/$b"
    }
}
