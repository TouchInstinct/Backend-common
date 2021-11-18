package ru.touchin.spring.workers.manager.core.repositories

import org.springframework.data.jpa.repository.JpaRepository
import ru.touchin.spring.workers.manager.core.models.Execution

interface ExecutionRepository : JpaRepository<Execution, String>
