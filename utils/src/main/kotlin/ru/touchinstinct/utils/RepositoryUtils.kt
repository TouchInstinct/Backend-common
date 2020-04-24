package ru.touchinstinct.utils

import org.springframework.data.repository.CrudRepository

fun <T : Any, ID> CrudRepository<T, ID>.update(entity: T, updater: T.() -> Unit): T {

    return save(entity.apply(updater))
}

fun <T : Any, ID> CrudRepository<T, ID>.upsert(entity: T, updater: (T) -> Unit): T {

    return save(entity.apply(updater))
}

fun <T : Any, ID> T.upsertInto(repository: CrudRepository<T, ID>, updater: (T) -> Unit): T {

    return repository.save(also(updater))
}

