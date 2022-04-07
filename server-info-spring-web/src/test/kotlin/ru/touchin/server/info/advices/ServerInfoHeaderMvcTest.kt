package ru.touchin.server.info.advices

import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping()
    fun wrap(): String {
        return "ok"
    }

}

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
internal class ServerInfoHeaderMvcTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("Результат должен иметь заголовок X-App-Build-Version")
    fun shouldBeWrappedResponse() {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/test"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.header().exists("X-App-Build-Version"))
    }

}
