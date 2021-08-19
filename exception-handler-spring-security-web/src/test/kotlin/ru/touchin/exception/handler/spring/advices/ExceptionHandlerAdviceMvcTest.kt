package ru.touchin.exception.handler.spring.advices

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/errors")
class ErrorController {


    @GetMapping("/unauthorized")
    fun runtimeError(): String {
        return "ok"
    }

}

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
internal class ExceptionHandlerAdviceMvcTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("Should be unauthorized")
    fun shouldGetUnauthorized() {
        mockMvc
            .perform(get("/api/errors/unauthorized"))
            .andExpect(status().isUnauthorized)
    }

}
