package com.rarible.opensea.client.deserializer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SaltDeserializerTest {

    private val deserializer = SaltDeserializer()

    @Test
    fun fixHex() {
        assertEquals("0x00", deserializer.fixHex("0x0"))
        assertEquals("0x012120", deserializer.fixHex("0x12120"))
        assertEquals("0x1212", deserializer.fixHex("0x1212"))
    }
}