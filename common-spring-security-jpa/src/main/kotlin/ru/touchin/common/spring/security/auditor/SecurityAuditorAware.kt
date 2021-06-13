@file:Suppress("unused")

package ru.touchin.common.spring.security.auditor

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

class SecurityAuditorAware(
    auditorResolversList: List<AuditorResolver>
) : AuditorAware<String> {

    private val auditorResolvers = auditorResolversList.asSequence()

    override fun getCurrentAuditor(): Optional<String> {
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .flatMap { principal: Any? ->
                val result = auditorResolvers
                    .mapNotNull{ it.resolve(principal) }
                    .firstOrNull()

                Optional.ofNullable(result)
            }
            .or { Optional.of(UNKNOWN_USER) }
    }

    companion object {
        const val UNKNOWN_USER = "unknownUser"
    }

}
