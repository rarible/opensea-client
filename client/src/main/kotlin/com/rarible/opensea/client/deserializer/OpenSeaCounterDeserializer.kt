package com.rarible.opensea.client.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.BigIntegerDeserializer
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import java.math.BigInteger

class OpenSeaCounterDeserializer : StdScalarDeserializer<BigInteger>(BigInteger::class.java) {

    private val delegate = BigIntegerDeserializer()

    override fun deserialize(source: JsonParser, ctxt: DeserializationContext?): BigInteger {
        val token = source.currentToken
        if (token == JsonToken.VALUE_STRING && source.text.startsWith("0x")) {
            return BigInteger(source.text.removePrefix("0x"), 16)
        }
        return delegate.deserialize(source, ctxt)
    }

}
