package ru.touchin.common.spring.processors

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.stereotype.Component
import ru.touchin.common.spring.annotations.RequiredBy

@Component
class RequiredByBeanDefinitionPostProcessor : BeanDefinitionRegistryPostProcessor {

    @Throws(BeansException::class)
    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
        for (beanName in registry.beanDefinitionNames) {
            val beanDefinition = registry.getBeanDefinition(beanName)
            if (beanDefinition.beanClassName == null) {
                continue
            }
            try {
                val beanClass = Class.forName(beanDefinition.beanClassName)
                if (beanClass.isAnnotationPresent(RequiredBy::class.java)) {
                    val dependantBeanNames = beanClass.getAnnotation(RequiredBy::class.java).value
                    for (dependantBeanName in dependantBeanNames) {
                        val dependantBeanDefinition = registry.getBeanDefinition(dependantBeanName)
                        dependantBeanDefinition.setDependsOn(beanName)
                    }
                }
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            }
        }
    }

    @Throws(BeansException::class)
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
    }
}
