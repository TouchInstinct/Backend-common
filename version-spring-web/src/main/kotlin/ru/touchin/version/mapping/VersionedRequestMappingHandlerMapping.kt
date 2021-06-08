package ru.touchin.version.mapping

import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import ru.touchin.version.annotations.VersionedApi
import java.lang.reflect.Method

class VersionedRequestMappingHandlerMapping : RequestMappingHandlerMapping() {

    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        val mappingResult = super.getMappingForMethod(method, handlerType)
            ?: return null

        val versionedAnnotation = AnnotatedElementUtils.findMergedAnnotation(handlerType, VersionedApi::class.java)
            ?: return mappingResult

        val versionPath = resolveEmbeddedValuesInPatterns(
            arrayOf(versionedAnnotation.value)
        )

        return RequestMappingInfo.paths(*versionPath).build().combine(mappingResult)
    }

}
