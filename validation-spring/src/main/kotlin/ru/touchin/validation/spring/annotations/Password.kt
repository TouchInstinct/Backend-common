package ru.touchin.validation.spring.annotations

import ru.touchin.validation.spring.validators.PasswordValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordValidator::class])
annotation class Password(
    val message: String = "{Password.invalid}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
