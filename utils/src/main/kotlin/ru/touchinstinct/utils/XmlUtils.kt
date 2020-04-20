package ru.touchinstinct.utils

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.ByteArrayOutputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

private val XML_DOCUMENT_BUILDER_FACTORY: DocumentBuilderFactory =
    DocumentBuilderFactory.newInstance().apply { isNamespaceAware = true }

private val transformerFactory = TransformerFactory.newInstance()

fun getXmlMinified(filename: String) = getResourceAsString(filename)
    .replace(">(\\s+)<".toRegex(), "><")

fun String.parseXmlDocument(): Document =
    XML_DOCUMENT_BUILDER_FACTORY.newDocumentBuilder().parse(byteInputStream())

fun Node.stringify(): String = DOMSource(this).stringify()

fun Source.stringify(): String {
    val streamOut = ByteArrayOutputStream()

    getDefaultTransformer().transform(this, StreamResult(streamOut))

    return streamOut.toString(Charsets.UTF_8.name())
}

fun NodeList.get(localName: String): Node? {
    for (i in 0 until length) {
        if (item(i).localName == localName) {
            return item(i)
        }
    }

    return null
}

private fun getDefaultTransformer(): Transformer =
    transformerFactory.newTransformer().apply {
        setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        setOutputProperty(OutputKeys.INDENT, "yes")
    }
