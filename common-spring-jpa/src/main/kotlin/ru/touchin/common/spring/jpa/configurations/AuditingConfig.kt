@file:Suppress("SpringFacetCodeInspection")
package ru.touchin.common.spring.jpa.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Optional

@Configuration
@EnableJpaAuditing(
    dateTimeProviderRef = "auditingDateTimeProvider",
    modifyOnCreate = false,
)
class AuditingConfig {

    @Bean
    fun auditingDateTimeProvider(): DateTimeProvider {
        return DateTimeProvider { Optional.of(ZonedDateTime.now(ZoneId.from(ZoneOffset.UTC))) }
    }

}
