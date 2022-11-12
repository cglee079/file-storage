package com.cglee079.file.storage.context

import java.util.*

object Context {

    private val xid: ThreadLocal<String> = ThreadLocal()

    fun xid(): String {
        if(xid.get() == null){
            init()
        }
        return xid.get()
    }

    fun init() {
        this.xid.set(UUID.randomUUID().toString())
    }

    fun clear() {
        this.xid.set(null)
    }
}
