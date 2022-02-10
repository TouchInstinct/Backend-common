package ru.touchin.spring.workers.manager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@SpringBootApplication
@TestConfiguration
@Import(WorkersManagerConfiguration::class)
class TestApplication
