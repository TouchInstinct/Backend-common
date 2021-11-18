package ru.touchin.spring.workers.manager.agent.utils

/**
 * Glob is simple replacement for Regex.
 * It uses only two special chars: * (any substring) and ? (any char)
 * This implementation has no support for characters escaping.
 * Glob has no capturing features.
 */
object Glob {

    private const val ANY_SUBSTRING_SYMBOL = '*'

    private const val ANY_CHAR_SYMBOL = '?'

    /**
     * Example: "I love patterns" matches "I * pa?t?er?s"
     *
     * Based on [StackOverflow answer](https://stackoverflow.com/a/3687031)
     */
    fun matches(text: String, pattern: String): Boolean {
        val starPosition = pattern.indexOf(ANY_SUBSTRING_SYMBOL)

        val headPattern: String = if (starPosition == -1) pattern else pattern.substring(0, starPosition)

        if (headPattern.length > text.length) {
            return false
        }

        // handle the part up to the first *
        for (i in headPattern.indices) {
            if (
                headPattern[i] != ANY_CHAR_SYMBOL
                && headPattern[i] != text[i]
            ) {
                return false
            }
        }

        if (starPosition == -1) {
            return headPattern.length == text.length
        }

        val tailPattern: String = pattern.substring(starPosition + 1)

        for (i in headPattern.length..text.length) {
            if (matches(text.substring(i), tailPattern)) {
                return true
            }
        }

        return false
    }

}
