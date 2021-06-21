package ru.touchin.validation.spring.validators

import org.springframework.beans.factory.annotation.Value
import ru.touchin.validation.spring.annotations.Email
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailValidator(
    @Value("\${validation.email.regex}")
    private val emailValidationRegex: Regex
) : ConstraintValidator<Email, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value?.matches(emailValidationRegex)
            ?: true
    }

}
