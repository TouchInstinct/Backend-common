package ru.touchinstinct.utils

import org.springframework.core.io.ClassPathResource
import java.io.InputStream
import java.math.BigDecimal
import java.util.Optional

fun getInputStreamOfResourceFile(fileName: String): InputStream = ClassPathResource(fileName).inputStream

fun getResourceAsString(filename: String): String = getInputStreamOfResourceFile(filename)
    .use { String(it.readBytes()) }

fun <T> Optional<T>.getOrNull(): T? = takeIf { it.isPresent }?.get()

fun BigDecimal.equalTo(number: BigDecimal): Boolean = compareTo(number) == 0
