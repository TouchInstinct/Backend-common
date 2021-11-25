package ru.touchin.spring.workers.manager.agent

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration

@SpringBootApplication
@TestConfiguration
@Import(WorkersManagerConfiguration::class)
class TestApplication
