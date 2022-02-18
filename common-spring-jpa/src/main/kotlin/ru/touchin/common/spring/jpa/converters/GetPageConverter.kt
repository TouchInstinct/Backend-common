package ru.touchin.common.spring.jpa.converters

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import ru.touchin.common.page.GetPage

fun GetPage.toPageable(): Pageable {
    return PageRequest.of(
        page - 1,
        size,
    )
}
