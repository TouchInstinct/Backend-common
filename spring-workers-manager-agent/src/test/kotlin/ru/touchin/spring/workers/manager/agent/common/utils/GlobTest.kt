package ru.touchin.spring.workers.manager.agent.common.utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GlobTest {

    @Test
    fun matches() {
        assertMatches("", "*****")
        assertMatches("my perfect text", "my*text")
        assertMatches("my perfect text", "my*?text")
        assertMatches("moon", "????")
        assertMatches("(abc)", "?abc?")
        assertMatches("****", "????")

        assertNotMatches("", "?")
        assertNotMatches("mo", "????")
        assertNotMatches("moonmoon", "????")
        assertNotMatches("my perfect text", "our*text")
    }

    private fun assertMatches(text: String, pattern: String) {
        assertTrue(Glob.matches(text, pattern))
    }

    private fun assertNotMatches(text: String, pattern: String) {
        assertFalse(Glob.matches(text, pattern))
    }

}
