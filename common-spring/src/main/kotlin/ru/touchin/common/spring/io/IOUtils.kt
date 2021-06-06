@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package ru.touchin.common.spring.io

import org.springframework.core.io.ClassPathResource
import java.io.InputStream

object IOUtils {

    fun getInputStreamOfResourceFile(fileName: String): InputStream = ClassPathResource(fileName).inputStream

    fun getResourceAsString(filename: String): String = getInputStreamOfResourceFile(filename)
        .use { String(it.readBytes()) }

}
