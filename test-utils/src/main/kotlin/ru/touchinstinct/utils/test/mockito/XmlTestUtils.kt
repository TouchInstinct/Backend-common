package ru.touchinstinct.utils.test.mockito

import java.io.StringWriter
import javax.xml.bind.JAXBContext

fun <T : Any> transformToXmlString(obj: T): String = StringWriter().let { stringWriter ->
    JAXBContext.newInstance(obj::class.java).createMarshaller().marshal(obj, stringWriter)
    stringWriter.toString()
}
