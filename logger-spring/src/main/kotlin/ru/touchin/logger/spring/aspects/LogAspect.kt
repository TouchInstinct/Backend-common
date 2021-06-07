package ru.touchin.logger.spring.aspects

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import ru.touchin.logger.spring.annotations.AutoLogging
import ru.touchin.logger.spring.annotations.LogValue
import ru.touchin.logger.builder.LogDataItem
import ru.touchin.logger.factory.LogBuilderFactory
import ru.touchin.logger.dto.LogDuration
import ru.touchin.logger.dto.LogError
import ru.touchin.logger.dto.LogValueField
import ru.touchin.logger.spring.serializers.LogValueFieldSerializer
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method

@Aspect
class LogAspect(
    private val logBuilderFactory: LogBuilderFactory<*>,
    private val logValueFieldSerializer: LogValueFieldSerializer,
) {

    @Around("@annotation(autoLoggingAnnotation)")
    fun logInvocation(pjp: ProceedingJoinPoint, autoLoggingAnnotation: AutoLogging): Any? {
        val duration = LogDuration()

        val actionResult = runCatching { pjp.proceed() }

        try {
            val method = pjp.method()

            logBuilderFactory.create(method.declaringClass)
                .setMethod(method.name)
                .setDuration(duration)
                .addTags(*autoLoggingAnnotation.tags)
                .addData(*getDataItems(pjp, actionResult).toTypedArray())
                .setError(actionResult.exceptionOrNull())
                .build()
                .log()
        } catch (error: Exception) {
            try {
                logBuilderFactory.create(this::class.java)
                    .addTags(LogError.ERROR_TAG, LogError.ERROR_FATAL_TAG)
                    .setError(error)
                    .build()
                    .error()
            } catch (logError: Throwable) {
                error.printStackTrace()
                logError.printStackTrace()
            }
        }

        return if (autoLoggingAnnotation.preventError) {
            actionResult.getOrNull()
        } else {
            actionResult.getOrThrow()
        }
    }

    private fun getDataItems(pjp: ProceedingJoinPoint, result: Result<Any?>): List<LogDataItem> {
        return getArgumentsField(pjp) + getResultFields(pjp.method(), result)
    }

    private fun getArgumentsField(pjp: ProceedingJoinPoint): List<LogDataItem> {
        return pjp.method().parameters
            .zip(pjp.args)
            .filter { (parameter, value) ->
                parameter.hasLogValueAnnotation() && value != null
            }
            .flatMap { (parameter, value) ->
                logValueFieldSerializer.invoke(
                    LogValueField(
                        name = parameter.name,
                        value = value,
                        prefix = parameter.getLogValuePrefix(),
                        expand = parameter.getLogValueExpand(),
                    )
                )
            }
    }

    private fun getResultFields(method: Method, result: Result<Any?>): List<LogDataItem> {
        if (result.isFailure || !method.hasLogValueAnnotation()) {
            return emptyList()
        }

        val returnValue = result.getOrNull() ?: return emptyList()

        return logValueFieldSerializer.invoke(
            LogValueField (
                name = null,
                value = returnValue,
                prefix = method.getLogValuePrefix(),
                expand = method.getLogValueExpand(),
            )
        )
    }

    companion object {
        private fun AnnotatedElement.hasLogValueAnnotation() = this.isAnnotationPresent(LogValue::class.java)

        private fun AnnotatedElement.getLogValuePrefix(): String? {
            val prefix = this.getAnnotation(LogValue::class.java).prefix.trim()

            if (prefix.isBlank()) {
                return null
            }

            return prefix
        }

        private fun AnnotatedElement.getLogValueExpand() = this.getAnnotation(LogValue::class.java).expand

        private fun ProceedingJoinPoint.method(): Method = (signature as MethodSignature).method
    }

}
