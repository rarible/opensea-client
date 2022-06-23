package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class SeaportOrderType(@get:JsonValue val value: String) {
    BASIC("basic"),
    ENGLISH_AUCTION("english")
    ;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): SeaportOrderType {
            return when (value) {
                BASIC.value -> BASIC
                ENGLISH_AUCTION.value -> ENGLISH_AUCTION
                else -> throw IllegalArgumentException("Unsupported value '$value'")
            }
        }
    }
}