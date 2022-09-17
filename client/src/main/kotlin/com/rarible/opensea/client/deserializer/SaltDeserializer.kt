package com.rarible.opensea.client.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import io.daonomic.rpc.domain.Binary
import scalether.abi.Uint256Type
import java.math.BigInteger

class SaltDeserializer : StdScalarDeserializer<BigInteger>(BigInteger::class.java) {
    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): BigInteger {
        return when (parser.currentToken) {
            JsonToken.VALUE_STRING,
            JsonToken.VALUE_NUMBER_INT -> {
                val value = parser.text.trim()
                if (value.startsWith("0x")) {
                    val fixedValue = fixHex(value)
                    Uint256Type.decode(Binary.apply(fixedValue), 0).value()
                } else {
                    BigInteger(value, 10)
                }
            }
            else -> ctxt.handleUnexpectedToken(_valueClass, parser) as BigInteger
        }
    }

    fun fixHex(hexString: String): String {
        if (hexString.length % 2 == 0) return hexString
        val hex = hexString.removePrefix("0x")
        return "0x0$hex"
    }
}