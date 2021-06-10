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
include("common-spring-test")
include("common-spring-test-jpa")
include("common-measure")
include("common-measure-spring")
include("common-geo")
include("common-geo-spatial4j-spring")
include("logger")
include("logger-spring")
include("logger-spring-web")
include("exception-handler-spring-web")
include("exception-handler-logger-spring-web")
include("version-spring-web")
include("response-wrapper-spring-web")
include("settings-spring-jpa")
