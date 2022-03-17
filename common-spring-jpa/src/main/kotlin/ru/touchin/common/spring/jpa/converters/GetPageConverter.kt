package ru.touchin.common.spring.jpa.converters

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import ru.touchin.common.page.GetPage

fun GetPage.toPageable(sort: Sort? = null): Pageable {
    val pageRequest = PageRequest.of(
        page - 1,
        size,
    )

    if (sort != null) {
        return pageRequest.withSort(sort)
    }

    return pageRequest
}
