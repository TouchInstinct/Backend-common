package ru.touchin.version

import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
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
import ru.touchin.version.annotations.VersionedApi

@VersionedApi
@RestController
@RequestMapping("/app")
class VersionController {

    @GetMapping("/version")
    fun version(): Map<String, String> {
        return mapOf("version" to "yes")
    }

}

@RestController
@RequestMapping("/app")
class NoVersionController {

    @GetMapping("/no-version")
    fun noVersion(): Map<String, String> {
        return mapOf("version" to "no")
    }

}

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
internal class VersionedRequestMappingHandlerMappingMvcTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @DisplayName("К пути должна добавляться версия")
    fun shouldBePathWithVersion() {
        mockMvc
            .perform(get("/api/v6/app/version"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.version", `is`("yes")))
    }

    @Test
    @DisplayName("К пути не должна добавляться версия")
    fun shouldBePathWithoutVersion() {
        mockMvc
            .perform(get("/app/no-version"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.version", `is`("no")))
    }

}
