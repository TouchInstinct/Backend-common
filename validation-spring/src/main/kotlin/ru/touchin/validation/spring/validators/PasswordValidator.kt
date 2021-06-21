package ru.touchin.validation.spring.validators

import org.springframework.beans.factory.annotation.Value
import ru.touchin.validation.spring.annotations.Password
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordValidator(
    @Value("\${validation.password.regex}")
    private val passwordValidationRegex: Regex
) : ConstraintValidator<Password, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value?.matches(passwordValidationRegex)
            ?: true
    }

}
