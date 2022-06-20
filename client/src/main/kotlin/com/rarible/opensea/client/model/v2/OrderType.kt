package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class OrderType(@get:JsonValue val value: String) {
    BASIC("basic")
    ;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): OrderType {
            return when (value) {
                BASIC.value -> BASIC
                else -> throw IllegalArgumentException("Unsupported value '$value'")
            }
        }
    }
}