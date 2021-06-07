package ru.touchin.exception.handler.spring.advices

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.spy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.exception.handler.spring.EnableSpringExceptionHandler
import ru.touchin.exception.handler.spring.creators.ExceptionResponseBodyCreator
import ru.touchin.exception.handler.spring.logger.Logger
import ru.touchin.exception.handler.spring.resolvers.FallbackExceptionResolver
import ru.touchin.exception.handler.spring.resolvers.IllegalStateExceptionResolver1
import ru.touchin.exception.handler.spring.resolvers.IllegalStateExceptionResolver2

@RestController
@RequestMapping("/api/errors")
class ErrorController {


    @GetMapping("/runtime")
    fun runtimeError() {
        throw RuntimeException("my runtime error")
    }

    @GetMapping("/illegal")
    fun illegalError() {
        throw IllegalStateException("my illegal error")
    }

}

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
internal class ExceptionHandlerAdviceMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var logger: Logger

    @Test
    @DisplayName("Тест должен проходить")
    fun shouldBeWork() {
        assertTrue(true, "Not passed")
    }

    @Test
    @DisplayName("Должна вернуться ошибка InternalServerError с кодом -1")
    fun shouldGetInternalServerError() {
        mockMvc
            .perform(get("/api/errors/runtime"))
            .andDo(print())
            .andExpect(status().isInternalServerError)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.errorCode", `is`(-1)))
            .andExpect(jsonPath("$.errorMessage", `is`("my runtime error")))
    }

    @Test
    @DisplayName("Должна вернуться ошибка BadRequest с кодом -2")
    fun shouldGetBadRequest() {
        mockMvc
            .perform(get("/api/errors/illegal"))
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.errorCode", `is`(-2)))
            .andExpect(jsonPath("$.errorMessage", `is`("my illegal error")))
    }

    @Test
    @DisplayName("Должен отработать только первый ExceptionResolver")
    fun shouldBeCorrectOrder() {
        val resolver1 = spy(IllegalStateExceptionResolver1::class.java)
        val resolver2 = spy(IllegalStateExceptionResolver2::class.java)
        val resolver3 = spy(FallbackExceptionResolver::class.java)

        val resolvers = listOf(resolver1, resolver2, resolver3)
        val exceptionResponseBodyCreator: ExceptionResponseBodyCreator = mock { }

        val exceptionHandlerAdvice = ExceptionHandlerAdvice(
            exceptionResolversList = resolvers,
            exceptionResponseBodyCreator = exceptionResponseBodyCreator,
            logger = logger
        )

        exceptionHandlerAdvice.handleException(IllegalStateException("error"))

        verify(resolver1, atLeastOnce()).invoke(any())
        verify(resolver2, never()).invoke(any())
        verify(resolver3, never()).invoke(any())
    }

}
