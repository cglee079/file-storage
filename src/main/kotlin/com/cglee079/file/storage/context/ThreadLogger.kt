package com.cglee079.file.storage.context

import com.cglee079.file.storage.util.DateTimeUtil.toISO
import net.logstash.logback.argument.StructuredArguments
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import kotlin.reflect.KClass

class ThreadLogger {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger("STASH_LOGGER")

        private val logData: ThreadLocal<MutableMap<String, String>> = ThreadLocal()

        private fun logData(): MutableMap<String, String> {
            if (logData.get() == null) {
                logData.set(mutableMapOf())
            }

            return logData.get()
        }

        fun init(xid: String) {
            logData.set(mutableMapOf())
            add("xid", xid)
        }

        fun appendMessage(clazz: KClass<*>, message: String) {
            val value = logData()["app.message"] ?: ""
            logData()["app.message"] = "${value}\n${LocalDateTime.now().toISO()} - ${clazz.qualifiedName} - $message"
        }

        fun add(key: String, value: String?) {
            if (value != null && value.isNotBlank()) {
                logData()[key] = value
            }
        }

        fun addAll(prefix: String, values: Map<String, Any>) {
            for (key in values.keys) {
                logData()["${prefix}.${key}"] = values[key].toString()
            }
        }


        fun addException(ex: Exception, metadata: Map<String, Any> = emptyMap()) {
            val value = "${ex.message} ($metadata)\n" + ex.stackTrace.filterIndexed { index, _ -> index < 5 }.joinToString ("\n") { it.toString() }
            add("app.exception", value)
        }

        @Synchronized
        fun info(message: String) {
            logger.info(message, StructuredArguments.entries(logData().toMap()))
            logData().clear()
        }
    }

}
