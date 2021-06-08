package ru.touchin.web.wrapper

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
import ru.touchin.wrapper.annotations.NoWrapResponse
import ru.touchin.wrapper.annotations.WrapResponse

@RestController
@WrapResponse
@RequestMapping("/wrapper")
class WrapperController {

    @GetMapping("/wrap")
    fun wrap(): Map<String, String> {
        return mapOf("wrap" to "yes")
    }

    @NoWrapResponse
    @GetMapping("/no-wrap")
    fun noWrap(): Map<String, String> {
        return mapOf("wrap" to "no")
    }

}

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
internal class WrapResponseAdviceMvcTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("Результат должен быть обернут")
    fun shouldBeWrappedResponse() {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/wrapper/wrap"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.wrap", Matchers.`is`("yes")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.`is`(0)))
    }

    @Test
    @DisplayName("Результат НЕ должен быть обернут")
    fun shouldBeNoWrappedResponse() {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/wrapper/no-wrap"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.wrap", Matchers.`is`("no")))
    }

}
