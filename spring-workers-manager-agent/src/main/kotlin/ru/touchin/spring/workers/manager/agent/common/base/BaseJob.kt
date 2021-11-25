package ru.touchin.spring.workers.manager.agent.common.base

interface BaseJob {

    fun run()

    fun getName(): String = this::class.java.name

}
