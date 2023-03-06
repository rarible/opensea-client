package com.rarible.opensea.client.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.math.BigInteger

class OpenSeaCounterDeserializerTest {

    private val mapper = ObjectMapper().registerModule(kotlinModule())

    @ParameterizedTest
    @CsvSource(
        value = [
            "0, 0", // Regular number
            "\"1\", 1", // Regular number - with quotes
            "\"0xa\", 10", // Short hex
            "\"0xbc8c686ea63e3fea5cce402b0f091e69\", 250623902904905483180184498125964648041", // Long hex
        ]
    )
    fun parse(jsonValue: String, expected: BigInteger) {
        val result = mapper.readValue("""{"counter": $jsonValue}""", TestCounter::class.java)
        assertEquals(expected, result.counter)
    }

    private data class TestCounter(
        @JsonDeserialize(using = OpenSeaCounterDeserializer::class)
        val counter: BigInteger
    )

}