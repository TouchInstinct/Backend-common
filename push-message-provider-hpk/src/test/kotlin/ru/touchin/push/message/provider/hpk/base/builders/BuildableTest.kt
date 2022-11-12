package ru.touchin.push.message.provider.hpk.base.builders

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.touchin.push.message.provider.hpk.base.extensions.ifNotNull

class BuildableTest {

    @Test
    fun ifNotNull_setOnNotNull() {
        val data = Data(property = true)
        val builder = DataBuilder()

        builder.ifNotNull(data.property) { setProperty(it) }

        Assertions.assertNotNull(
            builder.property
        )
    }

    @Test
    fun ifNotNull_notSetOnNull() {
        val data = Data(property = null)
        val builder = DataBuilder()

        builder.ifNotNull(data.property) { setProperty(it) }

        Assertions.assertNull(
            builder.property
        )
    }

    private class Data(val property: Boolean?)

    private class DataBuilder : Buildable {

        var property: Boolean? = null

        fun setProperty(property: Boolean): DataBuilder {
            this.property = property
            return this
        }

        fun build(): Data {
            return Data(
                property = property,
            )
        }

    }

}
