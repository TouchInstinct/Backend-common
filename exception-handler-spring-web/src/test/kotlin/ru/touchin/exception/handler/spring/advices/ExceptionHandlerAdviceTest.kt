package ru.touchin.exception.handler.spring.advices

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.spy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import ru.touchin.exception.handler.spring.creators.ExceptionResponseBodyCreator
import ru.touchin.exception.handler.spring.logger.Logger
import ru.touchin.exception.handler.spring.properties.ExceptionResolverProperties
import ru.touchin.exception.handler.spring.resolvers.FallbackExceptionResolver
import ru.touchin.exception.handler.spring.resolvers.IllegalStateExceptionResolver1
import ru.touchin.exception.handler.spring.resolvers.IllegalStateExceptionResolver2

@ActiveProfiles("test")
@SpringBootTest
internal class ExceptionHandlerAdviceTest {

    private lateinit var resolver1: IllegalStateExceptionResolver1
    private lateinit var resolver2: IllegalStateExceptionResolver2
    private lateinit var resolver3: FallbackExceptionResolver

    private lateinit var exceptionHandlerAdvice: ExceptionHandlerAdvice

    @Autowired
    lateinit var logger: Logger

    @BeforeEach
    fun setUp() {
        resolver1 = spy(IllegalStateExceptionResolver1::class.java)
        resolver2 = spy(IllegalStateExceptionResolver2::class.java)
        resolver3 = spy(FallbackExceptionResolver::class.java)

        val resolvers = listOf(resolver1, resolver2, resolver3)
        val exceptionResponseBodyCreator: ExceptionResponseBodyCreator = mock { }

        exceptionHandlerAdvice = ExceptionHandlerAdvice(
            exceptionResolversList = resolvers,
            exceptionResponseBodyCreator = exceptionResponseBodyCreator,
            logger = logger,
            exceptionResolverProperties = ExceptionResolverProperties()
        )
    }

    @Test
    @DisplayName("Должен отработать только первый ExceptionResolver")
    fun shouldBeExecuteOnlyResolver1() {
        exceptionHandlerAdvice.handleException(IllegalStateException("error"))

        verify(resolver1, only()).invoke(any())
        verify(resolver2, never()).invoke(any())
        verify(resolver3, never()).invoke(any())
    }

    @Test
    @DisplayName("Должны отработать все resolvers и поймать ошибку в FallbackResolver")
    fun shouldBeExecuteAllResolversAndResolvedInFallback() {
        exceptionHandlerAdvice.handleException(RuntimeException("error"))

        verify(resolver1, only()).invoke(any())
        verify(resolver2, only()).invoke(any())
        verify(resolver3, only()).invoke(any())
    }

    @Test
    @DisplayName("Должны отработать все resolvers и НЕ поймать ошибку в FallbackResolver")
    fun shouldVBeExecuteAllResolversWithoutResolve() {
        val resolvers = listOf(resolver1, resolver2)
        val exceptionResponseBodyCreator: ExceptionResponseBodyCreator = mock { }

        exceptionHandlerAdvice = ExceptionHandlerAdvice(
            exceptionResolversList = resolvers,
            exceptionResponseBodyCreator = exceptionResponseBodyCreator,
            logger = logger,
            exceptionResolverProperties = ExceptionResolverProperties()
        )

        exceptionHandlerAdvice.handleException(RuntimeException("error"))

        verify(resolver1, only()).invoke(any())
        verify(resolver2, only()).invoke(any())
    }

}
