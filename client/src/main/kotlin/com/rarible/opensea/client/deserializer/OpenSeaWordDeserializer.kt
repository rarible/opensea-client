package com.rarible.opensea.client.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import io.daonomic.rpc.domain.Word

class OpenSeaWordDeserializer : StdScalarDeserializer<Word>(Word::class.java) {
    override fun deserialize(source: JsonParser, ctxt: DeserializationContext): Word? {
        val token = source.currentToken
        return if (token == JsonToken.VALUE_STRING) {
            val string = source.text.removePrefix("0x")

            val fixedString = when (string.length) {
                62 -> string + "00"
                63 -> string + "0"
                40 -> string + "000000000000000000000000"
                else -> string
            }
            if (fixedString.isNotBlank()) Word.apply(fixedString) else null
        } else {
            ctxt.handleUnexpectedToken(_valueClass, source) as Word
        }
    }
}