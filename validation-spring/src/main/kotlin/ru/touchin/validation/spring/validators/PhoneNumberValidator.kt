package ru.touchin.validation.spring.validators

import org.springframework.beans.factory.annotation.Value
import ru.touchin.validation.spring.annotations.PhoneNumber
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PhoneNumberValidator(
    @Value("\${validation.phone.regex:$DEFAULT_PHONE_REGEX}")
    private val phoneValidationRegex: Regex
) : ConstraintValidator<PhoneNumber, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        return value?.matches(phoneValidationRegex)
            ?: true
    }

    companion object {
        private const val DEFAULT_PHONE_REGEX = "\\+7\\d{10}"
    }

}
