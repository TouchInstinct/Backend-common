package ru.touchinstinct.utils

import org.w3c.dom.Node
import javax.xml.bind.JAXBContext

inline fun <reified T> unmarshalRequestFrom(xml: Node): T = unmarshalRequestFrom(xml, T::class.java)

fun <T> unmarshalRequestFrom(xml: Node, clazz: Class<T>): T {
    val jaxbUnmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller()

    return jaxbUnmarshaller.unmarshal(xml, clazz).value
}
