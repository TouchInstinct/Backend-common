package ru.touchinstinct.utils

import org.jetbrains.annotations.Contract

private val TRANSLITERATION_TABLE: Map<Char, String> = mapOf(
    ' ' to " ",
    'а' to "a",
    'б' to "b",
    'в' to "v",
    'г' to "g",
    'д' to "d",
    'е' to "e",
    'ё' to "e",
    'ж' to "zh",
    'з' to "z",
    'и' to "i",
    'й' to "y",
    'к' to "k",
    'л' to "l",
    'м' to "m",
    'н' to "n",
    'о' to "o",
    'п' to "p",
    'р' to "r",
    'с' to "s",
    'т' to "t",
    'у' to "u",
    'ф' to "f",
    'х' to "h",
    'ц' to "ts",
    'ч' to "ch",
    'ш' to "sh",
    'щ' to "sch",
    'ъ' to "'",
    'ы' to "i",
    'ь' to "'",
    'э' to "e",
    'ю' to "ju",
    'я' to "ja",
    'А' to "A",
    'Б' to "B",
    'В' to "V",
    'Г' to "G",
    'Д' to "D",
    'Е' to "E",
    'Ё' to "E",
    'Ж' to "Zh",
    'З' to "Z",
    'И' to "I",
    'Й' to "Y",
    'К' to "K",
    'Л' to "L",
    'М' to "M",
    'Н' to "N",
    'О' to "O",
    'П' to "P",
    'Р' to "R",
    'С' to "S",
    'Т' to "T",
    'У' to "U",
    'Ф' to "F",
    'Х' to "H",
    'Ц' to "Ts",
    'Ч' to "Ch",
    'Ш' to "Sh",
    'Щ' to "Sch",
    'Ъ' to "'",
    'Ы' to "I",
    'Ь' to "'",
    'Э' to "E",
    'Ю' to "Ju",
    'Я' to "Ja"
)

object StringUtils {

    @Contract("hello_world -> helloWorld; HeLlO_WORld -> helloWorld; HELLO_WORLD -> helloWorld", pure = true)
    fun snakeCaseToCamelCase(string: String): String {
        var nextUpperCase = false

        return buildString {
            for (char in string) {
                when {
                    char == '_' -> {
                        nextUpperCase = true
                    }

                    nextUpperCase -> {
                        this.append(char.toUpperCase())
                        nextUpperCase = false
                    }

                    !nextUpperCase -> {
                        this.append(char.toLowerCase())
                    }
                }
            }
        }
    }
}

fun String.removeNonPrintableCharacters(): String {
    return this
        .transliterateCyrillic()
        .replace("[\\p{Cntrl}&&[^\r\n\t]]".toRegex(), "")// erases all the ASCII control characters
        .replace("\\p{C}".toRegex(), "")// removes non-printable characters from Unicode
        .trim()
}

fun String.transliterateCyrillic(): String {
    val builder = StringBuilder()
    for (char in this) {
        if (TRANSLITERATION_TABLE.containsKey(char)) {
            builder.append(TRANSLITERATION_TABLE[char])
        } else {
            builder.append(char)
        }
    }
    return builder.toString()
}

fun String?.orNullIfBlank(): String? = this?.takeIf { it.isNotBlank() }

fun String?.orIfNullOrBlank(altValue: String): String = if (this.isNullOrBlank()) altValue else this
