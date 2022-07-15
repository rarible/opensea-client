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
                WORD_STRING_LENGTH -> string
                else -> EMPTY_WORD.slice(IntRange(string.length, WORD_STRING_LENGTH - 1)) + string
            }
            if (fixedString.isNotBlank()) Word.apply(fixedString) else null
        } else {
            ctxt.handleUnexpectedToken(_valueClass, source) as Word
        }
    }

    private companion object {
        const val EMPTY_WORD = "0000000000000000000000000000000000000000000000000000000000000000"
        const val WORD_STRING_LENGTH = 64
    }
}