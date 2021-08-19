package ru.touchin.logger.serializers.impl

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.touchin.logger.dto.LogValueField
import ru.touchin.logger.spring.serializers.LogValueFieldSerializer
import java.io.File
import java.math.BigDecimal
import java.net.URI
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

@SpringBootTest
internal class LogValueFieldResolverImplTest {
    private val birthDate = "2020-05-15"

    @Autowired
    private lateinit var logValueFieldSerializer: LogValueFieldSerializer

    private val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)

    @Suppress("unused")
    class Pet(
        val nickname: String,
        val weight: Double,
        val hasTail: Boolean,
    )

    @Suppress("unused")
    class User(
        val name: String,
        val age: Int?,
        val birthDate: LocalDate = LocalDate.parse("2020-05-12"),
        val pet: Pet? = null
    )

    @Test
    @DisplayName("Префикс должен быть приоритетнее имени")
    fun shouldUsePrefixInsteadName() {
        val value = 25

        val logValueField = LogValueField(
            name = "myAge",
            value = value,
            prefix = "prefixName",
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals(logValueField.prefix, field.first)
    }

    @Test
    @DisplayName("Если имя не указано, то название параметра должно быть result")
    fun shouldBeFieldNameResultIfNameOmit() {
        val value = "john"

        val logValueField = LogValueField(
            name = null,
            value = value,
            prefix = null,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("result", field.first)
    }

    @Test
    @DisplayName("Для примитивных аргументов должен добавляться суффикс с типом")
    fun shouldBeSuffixTypeForPrimitiveArguments() {
        val value = 25

        val logValueField = LogValueField(
            name = "age",
            value = value,
            prefix = null,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertTrue(field.first == "age_number")
    }

    @Test
    @DisplayName("Для примитивных значение в return должен добавляться префикс")
    fun shouldBeSuffixTypeForPrimitiveResult() {
        val value = true

        val logValueField = LogValueField(
            name = null,
            value = value,
            prefix = null,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("result_boolean", field.first)
    }

    @Test
    @DisplayName("Объект должен быть строкой, если не указан expand")
    fun shouldSerializeObjectValue() {
        val value = User(
            name = "john",
            age = 15,
            birthDate = LocalDate.parse(birthDate)
        )

        val logValueField = LogValueField(
            name = "user",
            value = value,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("user", field.first)

        assertTrue(field.second is String)
        assertEquals(objectMapper.writeValueAsString(value), field.second)
    }

    @Test
    @DisplayName("Объекты со значением Unit должны отфильтровываться")
    fun shouldOmitUnit() {
        val logValueField = LogValueField(
            name = "null",
            value = Unit,
            prefix = "prefixName",
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertTrue(result.isEmpty())
    }

    @Test
    @DisplayName("Объекты со значением Function должны отфильтровываться")
    fun shouldOmitFunction() {
        val logValueField = LogValueField(
            name = "null",
            value = {},
            prefix = "prefixName",
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertTrue(result.isEmpty())
    }

    @Test
    @DisplayName("Если указан expand для объекта, то его поля поднимаются на один уровень с аргументами")
    fun shouldExpandObjectValues() {
        val value = User(
            name = "john",
            age = 15,
            birthDate = LocalDate.parse(birthDate)
        )

        val logValueField = LogValueField(
            name = "user",
            value = value,
            expand = true
        )

        val result = logValueFieldSerializer.invoke(logValueField).sortedBy { it.first }
        assertEquals(3, result.size)


        val (ageItem, birthDateItem, nameItem) = result

        assertEquals("age_number", ageItem.first)
        assertEquals(value.age, ageItem.second)

        assertEquals("birthDate_date", birthDateItem.first)
        assertEquals(birthDate, birthDateItem.second)

        assertEquals("name", nameItem.first)
        assertEquals(value.name, nameItem.second)

    }

    @Test
    @DisplayName("Если expand объекта есть поле-объект, то это поле сериализуется в строку")
    fun shouldSerializeObjectIfParentExpand() {
        val value = User(
            name = "john",
            age = 15,
            birthDate = LocalDate.parse(birthDate),
            pet = Pet("Rex", 56.13, false)
        )

        val logValueField = LogValueField(
            name = "user",
            value = value,
            expand = true
        )

        val result = logValueFieldSerializer.invoke(logValueField).sortedBy { it.first }
        assertEquals(4, result.size)

        val (_, _, _, petItem) = result

        assertEquals("pet", petItem.first)
        assertTrue(petItem.second is String)
        assertEquals(objectMapper.writeValueAsString(value.pet), petItem.second)
    }

    @Test
    @DisplayName("Если указан expand с префиксом, то названия полей формируется как `prefix.field`")
    fun shouldExpandObjectWithPrefix() {
        val value = User(
            name = "john",
            age = 15,
            birthDate = LocalDate.parse(birthDate),
            pet = Pet("Rex", 56.13, false)
        )

        val logValueField = LogValueField(
            name = "object",
            value = value,
            prefix = "user",
            expand = true
        )

        val result = logValueFieldSerializer
            .invoke(logValueField)
            .map { it.first }
            .sorted()

        assertEquals(4, result.size)
        assertEquals(listOf("user.age", "user.birthDate", "user.name", "user.pet"), result)
    }

    @Test
    @DisplayName("Если указан expand для return, то его поля попадают в список")
    fun shouldExpandReturnValues() {
        val value = User(
            name = "john",
            age = 15,
            birthDate = LocalDate.parse(birthDate)
        )

        val logValueField = LogValueField(
            name = null,
            value = value,
            expand = true
        )

        val result = logValueFieldSerializer.invoke(logValueField).sortedBy { it.first }
        assertEquals(3, result.size)

        val (ageItem, birthDateItem, nameItem) = result

        assertEquals("age_number", ageItem.first)
        assertEquals(value.age, ageItem.second)

        assertEquals("birthDate_date", birthDateItem.first)
        assertEquals(birthDate, birthDateItem.second)

        assertEquals("name", nameItem.first)
        assertEquals(value.name, nameItem.second)
    }

    @Test
    @DisplayName("Если указан expand для return и префикс, то его поля попадают в список с названием `prefix.field`")
    fun shouldExpandReturnWithPrefix() {
        val value = User(
            name = "john",
            age = 15,
            birthDate = LocalDate.parse(birthDate),
            pet = Pet("Rex", 56.13, false)
        )

        val logValueField = LogValueField(
            name = null,
            value = value,
            prefix = "user",
            expand = true
        )

        val result = logValueFieldSerializer
            .invoke(logValueField)
            .map { it.first }
            .sorted()

        assertEquals(4, result.size)
        assertEquals(listOf("user.age", "user.birthDate", "user.name", "user.pet"), result)
    }

    @Test
    @DisplayName("Даты должны сериализоваться в строку")
    fun shouldBeDateString() {
        val date = "2020-12-21T12:09:12Z"
        val value = ZonedDateTime.parse(date)

        val logValueField = LogValueField(
            name = "birthdate",
            value = value,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("birthdate_date", field.first)
        assertEquals(date, field.second)
    }

    @Test
    @DisplayName("URI должны сериализоваться в строку")
    fun shouldBeUriString() {
        val uri = URI.create("http://example.com/test")

        val logValueField = LogValueField(
            name = "url",
            value = uri,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("url", field.first)
        assertEquals(field.second, uri.toString())
    }

    @Test
    @DisplayName("URL должны сериализоваться в строку")
    fun shouldBeUrlString() {
        val url = URI.create("http://example.com/test").toURL()

        val logValueField = LogValueField(
            name = "url",
            value = url,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("url", field.first)
        assertEquals(field.second, url.toString())
    }

    @Test
    @DisplayName("Files должны сериализоваться в строку")
    fun shouldFilePath() {
        val path = "/tmp/filename"
        val file = File(path)

        val logValueField = LogValueField(
            name = "file",
            value = file,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("file", field.first)
        assertEquals(field.second, path)
    }

    @Test
    @DisplayName("UUID должны сериализоваться в строку")
    fun shouldBeUUIDString() {
        val uuidString = "f1036cc5-2d86-4dde-b27c-820fb11f1974"
        val uuid = UUID.fromString(uuidString)

        val logValueField = LogValueField(
            name = "uuid",
            value = uuid,
        )

        val result = logValueFieldSerializer.invoke(logValueField)
        assertEquals(1, result.size)

        val field = result.first()
        assertEquals("uuid", field.first)
        assertEquals(field.second, uuidString)
    }

    @Test
    @DisplayName("Number не должен сериализоваться в строку")
    fun shouldBeInt() {
        val b: Byte = 128.toByte()
        val s: Short = 256
        val i = 512
        val l = 1024L
        val f = 1.2f
        val d = 12.13
        val bd: BigDecimal = BigDecimal.valueOf(12.10)

        val values = listOf(
            b, s, i, l, f, d, bd
        )

        values.forEach { value ->
            val logValueField = LogValueField(
                name = null,
                value = value,
            )

            val result = logValueFieldSerializer.invoke(logValueField).also {
                assertEquals(1, it.size)
            }

            val field = result.first()
            assertEquals("result_number", field.first)
            assertEquals(value, field.second)
        }

    }

}
