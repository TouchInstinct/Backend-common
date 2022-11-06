package ru.touchin.auth.core.scope.repositories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import ru.touchin.auth.core.scope.exceptions.ScopeNotFoundException
import ru.touchin.auth.core.scope.models.ScopeEntity
import ru.touchin.auth.core.scope.models.ScopeGroupEntity
import ru.touchin.common.spring.test.jpa.repository.RepositoryTest
import java.time.ZonedDateTime
import javax.persistence.EntityManager

@RepositoryTest
internal class ScopeRepositoryTest {

    @Autowired
    private lateinit var scopeRepository: ScopeRepository

    @Autowired
    private lateinit var scopeGroupRepository: ScopeGroupRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    fun createScope(): ScopeEntity {
        return ScopeEntity()
            .apply {
                name = "admin"
                createdAt = ZonedDateTime.now()
            }
            .also { scope ->
                scopeRepository.saveAndFlush(scope)
            }

    }

    @Test
    @DisplayName("Можно создать `ScopeModel`")
    fun scopeShouldBeCreated() {
        val scope = createScope()

        entityManager.clear()

        val savedScope = scopeRepository.findByIdOrThrow(scope.name)

        assertTrue(
            ReflectionEquals(scope, "createdAt", "users").matches(savedScope)
        )
    }

    @Test
    @DisplayName("Имя scope должно быть уникалным")
    fun scopeShouldBeUniqName() {
        scopeGroupRepository.deleteAll()
        scopeRepository.deleteAll()

        createScope()

        entityManager.clear()

        createScope()

        entityManager.clear()

        val scopes = scopeRepository.findAll()

        assertEquals(1, scopes.size)
    }

    @Test
    @DisplayName("Если scope не найден, то должна быть ошибка ScopeNotFoundException")
    fun shouldBeScopeNotFoundException() {
        val missingScope = "missing"

        assertThrows(ScopeNotFoundException::class.java) {
            scopeRepository.findByIdOrThrow(missingScope)
        }
    }

    @Test
    @DisplayName("Должен быть дефолтный scope")
    fun shouldBeDefaultScope() {
        val scopes = scopeRepository.findByGroup(ScopeGroupEntity.DEFAULT_USER_SCOPE_GROUP)

        assertEquals(1, scopes.size)
    }

}
