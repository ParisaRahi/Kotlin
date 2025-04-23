package com.example.parisara_oblig2.data

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

class XMLParser {

    //don't use nameSpace
    private val ns: String? = null

    //instantiate the parser
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Dis3> {//return a list of Dist3
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    //Read the feed
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Dis3> {
        val dis3List = mutableListOf<Dis3>()

        parser.require(XmlPullParser.START_TAG, ns, "districtThree")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag
            if (parser.name == "party") {//when this finds party, then create a Dis3 object and add to list
                dis3List.add(readEntry(parser))
            } else {
                skip(parser)
            }
        }
        return dis3List
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): Dis3 {
        parser.require(XmlPullParser.START_TAG, ns, "party")
        var id = 0
        var votes = 0

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "id" -> id = readAttribute(parser, parser.name).toInt()
                "votes" -> votes = readAttribute(parser, parser.name).toInt()
                else -> skip(parser)
            }
        }
        return Dis3(id, votes)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttribute(parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        val value = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return value
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    //skip attributes that we vil not
    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

}