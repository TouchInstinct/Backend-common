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
            val beanClassName = registry.getBeanDefinition(beanName).beanClassName?:continue

            getDependantBeanNames(beanClassName).forEach { dependantBeanName ->
                val dependantBeanDefinition = registry.getBeanDefinition(dependantBeanName)
                dependantBeanDefinition.setDependsOn(beanName)
            }
        }
    }

    @Throws(BeansException::class)
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
    }

    private fun getDependantBeanNames(beanClassName: String): List<String> {
        val beanClass = Class.forName(beanClassName)
        var dependantBeans = emptyList<String>()

        if (beanClass.isAnnotationPresent(RequiredBy::class.java)) {
            dependantBeans = beanClass.getAnnotation(RequiredBy::class.java).value.toList()
        }

        return dependantBeans
    }
}
