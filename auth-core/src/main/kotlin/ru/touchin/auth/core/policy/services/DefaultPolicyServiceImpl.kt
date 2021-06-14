package ru.touchin.auth.core.policy.services

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Service
import ru.touchin.auth.core.policy.dto.RegistrationPolicy

@Service
class DefaultPolicyServiceImpl : PolicyService {

    override fun getRegistrationPolicy(): RegistrationPolicy {
        return RegistrationPolicy(
            multiAccountsPerDevice = false
        )
    }

}
