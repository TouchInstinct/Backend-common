package ru.touchinstinct.utils

import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

object ReactorUtils {
    fun <T : Any?> blocking(block: () -> T): Mono<T> =
        Mono.fromCallable(block).subscribeOn(Schedulers.elastic())
}

fun <T : Any?, Y : Any?> Mono<T>.mapElastic(block: (T) -> Y): Mono<Y> =
    flatMap { argument ->
        Mono.fromCallable { block(argument) }
            .subscribeOn(Schedulers.elastic())
    }
