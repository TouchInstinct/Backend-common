package ru.touchin.validation.spring.validators

import org.springframework.beans.factory.annotation.Value
import ru.touchin.validation.spring.annotations.PhoneNumber
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PhoneNumberValidator(
    @Value("\${validation.phone.regex}")
    private val phoneValidationRegex: Regex
) : ConstraintValidator<PhoneNumber, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value?.matches(phoneValidationRegex)
            ?: true
    }

}
