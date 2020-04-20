package ru.touchinstinct.utils.test.spring.data

import org.springframework.data.repository.CrudRepository
import ru.touchinstinct.utils.test.mockito.MockitoUtils.anyx
import ru.touchinstinct.utils.test.mockito.MockitoUtils.mock
import java.lang.reflect.Field
import java.util.Optional
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.absoluteValue
import org.mockito.Mockito.`when` as on

/**
 * This class is used to set up mock of repository
 **/
class MockRepository<R : CrudRepository<T, ID>, T : Any, ID>(
        val mock: R, // repository mock
        private val entities: MutableList<T> = ArrayList(),
        private val idExtractor: ((T) -> ID?)? = null
) {

    companion object {
        @Deprecated("idExtractor is no more required", ReplaceWith("MockRepository.create()"))
        inline fun <reified R : CrudRepository<T, ID>, reified T : Any, reified ID> mockRepository(
                noinline idExtractor: (T) -> ID?
        ): R = MockRepository.create()

        @Deprecated("idExtractor is no more required", ReplaceWith("MockRepository.create(settings)"))
        inline fun <reified R : CrudRepository<T, ID>, reified T : Any, reified ID> mockRepository(
                noinline idExtractor: (T) -> ID?,
                noinline settings: (R, MutableList<T>) -> Unit
        ): R = MockRepository.create(settings)

        inline fun <reified R : CrudRepository<T, ID>, reified T : Any, reified ID> create(
                noinline settings: (R, MutableList<T>) -> Unit
        ): R {
            val entities = ArrayList<T>()

            return MockRepository(mock<R>(), entities).mock
                    .also { settings(it, entities) }
        }

        inline fun <reified R : CrudRepository<T, ID>, reified T : Any, reified ID> create(): R {
            val entities = ArrayList<T>()

            return MockRepository(mock<R>(), entities).mock
        }

    }

    init {
        init()
    }

    private fun init() {
        on(mock.findAll()).thenAnswer {
            entities
        }

        on(mock.save(anyx<T>())).thenAnswer { it ->
            save(it.getArgument(0))
        }

        on(mock.saveAll(anyx<List<T>>())).thenAnswer { it ->
            saveAll(it.getArgument(0))
        }

        // 'findById' method does not check if id is unique.
        on(mock.findById(anyx())).thenAnswer { it ->
            findById(it.getArgument(0))
        }

        // 'findById' method does not check if id is unique.
        on(mock.deleteAll()).thenAnswer { reset() }

        on(mock.delete(anyx())).thenAnswer { delete(it.getArgument(0)) }

    }

    private fun findById(id: ID): Optional<T> {
        return Optional.ofNullable(entities.find { id(it) == id })
    }

    private fun save(argument: T): T {
        val id = id(argument) ?: trySetId(argument)

        entities.replaceAll { entity -> if (id(entity) == id) argument else entity }

        if (entities.find { entity -> id(entity) == id } == null) {
            entities.add(argument)
        }

        return argument
    }

    private fun saveAll(objects: List<T>): List<T> {
        return objects.map { save(it) }
    }

    private fun delete(any: T) = entities.remove(any)

    fun reset() {
        entities.clear()
    }

    private fun idField(entity: T): Field {
        val javaClass = (entity as Any).javaClass

        val fields = javaClass.declaredFields.toSet() + javaClass.superclass.declaredFields.toSet()

        val idFieldOrNull = fields
                .firstOrNull {
                    it.annotations.any { annotation -> annotation.annotationClass.simpleName == "Id" }
                }
                ?.also { it.isAccessible = true }

        return idFieldOrNull
                ?: throw IllegalStateException("Type ${javaClass.canonicalName} has no field with @Id annotation.\n" +
                        "Fields: ${fields.map { it.name }}")
    }

    private fun id(entity: T): ID {
        val idField = idField(entity)

        @Suppress("UNCHECKED_CAST")
        return idField.get(entity) as ID
    }

    private fun trySetId(entity: T): ID = idField(entity).let { idField ->
        val id = when (idField.type) {
            java.lang.String::class.java, String::class.java -> UUID.randomUUID().toString()
            java.lang.Long::class.java, Long::class.java -> ThreadLocalRandom.current().nextLong().absoluteValue
            else -> throw IllegalAccessException("Could not generate id of type ${idField.type.canonicalName}")
        }

        idField.set(entity, id)

        @Suppress("UNCHECKED_CAST")
        id as ID
    }


}
