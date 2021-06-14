package ru.touchin.auth.core.policy.services

import ru.touchin.auth.core.policy.dto.RegistrationPolicy

interface PolicyService {

    fun getRegistrationPolicy(): RegistrationPolicy

}
