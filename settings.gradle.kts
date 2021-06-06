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
