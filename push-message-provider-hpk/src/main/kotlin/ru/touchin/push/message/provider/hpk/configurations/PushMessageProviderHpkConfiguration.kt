package ru.touchin.push.message.provider.hpk.configurations

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.support.SimpleCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import ru.touchin.push.message.provider.configurations.PushMessageProviderConfiguration
import ru.touchin.push.message.provider.hpk.services.HmsOauthAccessTokenCacheServiceImpl.Companion.HMS_CLIENT_SERVICE_CACHE_KEY

@ComponentScan("ru.touchin.push.message.provider.hpk")
@ConfigurationPropertiesScan(basePackages = ["ru.touchin.push.message.provider.hpk"])
@Import(value = [PushMessageProviderConfiguration::class])
class PushMessageProviderHpkConfiguration {

    @Bean("push-message-provider.hpk.webclient-objectmapper")
    fun webclientObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Bean("push-message-provider.hpk.client-objectmapper")
    fun clientObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }

    @Bean("push-message-provider.hpk.webclient-cachemanager")
    @ConditionalOnMissingBean
    fun cacheManager(): CacheManager {
        return SimpleCacheManager().also {
            it.setCaches(
                listOf(
                    ConcurrentMapCache(HMS_CLIENT_SERVICE_CACHE_KEY)
                )
            )
        }
    }

}
