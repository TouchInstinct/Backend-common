package ru.touchin.common.spring.web.dto

interface ApiError {

    val errorCode: Int
    val errorMessage: String?

    companion object {
        const val SUCCESS_CODE = 0
        const val FAILURE_CODE = -1
    }

}
