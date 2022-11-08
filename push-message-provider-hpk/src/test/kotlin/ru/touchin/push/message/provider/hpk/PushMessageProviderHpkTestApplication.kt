package ru.touchin.push.message.provider.hpk

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import
import ru.touchin.logger.spring.configurations.SpringLoggerConfiguration
import ru.touchin.logger.spring.web.configurations.SpringLoggerWebConfiguration

@TestConfiguration
@SpringBootConfiguration
@EnablePushMessageProviderHpk
@Import(
    SpringLoggerConfiguration::class,
    SpringLoggerWebConfiguration::class,
)
class PushMessageProviderHpkTestApplication
