package ru.touchin.push.message.provider.hpk.base.enums

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ValueableSerializableEnumTest {

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun toValue_correctJacksonSerialization() {
        val enum = TestEnum.VALUE1

        val expected = enum.toValue()

        val actual = objectMapper.writeValueAsString(enum)
            .let(objectMapper::readTree)
            .textValue()

        Assertions.assertEquals(
            expected,
            actual
        )
    }

    private enum class TestEnum(override val value: String) : ValueableSerializableEnum<String> {

        VALUE1("testValue1"),

    }

}
