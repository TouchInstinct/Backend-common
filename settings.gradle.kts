pluginManagement {
    resolutionStrategy {
        val kotlinVersion: String by settings
        val springBootVersion: String by settings
        val springDependencyManagementVersion: String by settings

        val versions = listOf(
            "org.jetbrains.kotlin" to kotlinVersion,
            "org.springframework.boot" to springBootVersion,
            "io.spring.dependency-management" to springDependencyManagementVersion
        )

        eachPlugin {
            versions.find { requested.id.id.startsWith(it.first) }
                ?.let {
                    useVersion(it.second)
                }
        }
    }
}

include("common")
include("common-spring")
include("common-spring-jpa")
include("common-spring-web")
include("common-spring-security")
include("common-spring-security-jpa")
include("common-spring-test")
include("common-spring-test-jpa")
include("common-measure")
include("common-measure-spring")
include("common-territories")
include("common-device")
include("common-geo")
include("common-geo-spatial4j-spring")
include("common-messaging")
include("captcha")
include("logger")
include("logger-spring")
include("logger-spring-web")
include("exception-handler-spring-web")
include("exception-handler-spring-security-web")
include("exception-handler-logger-spring-web")
include("validation-spring")
include("version-spring-web")
include("push-message-provider")
include("push-message-provider-fcm")
include("push-message-provider-hpk")
include("push-message-provider-mock")
include("response-wrapper-spring-web")
include("settings-spring-jpa")
include("security-authorization-server-core")
include("security-authorization-server-jwt-core")
include("security-authorization-server-oauth2-metadata")
include("security-resource-server-default-jwt-configuration")
include("security-resource-server-custom-jwt-configuration")
include("security-resource-server-test-jwt-configuration")
include("security-jwt-common")
include("s3-storage")
include("server-info-spring-web")
include("geoip-core")
include("user-agent")
