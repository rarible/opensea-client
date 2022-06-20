package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class OrderSide(@get:JsonValue val value: String) {
    BUY("offer"),
    SELL("ask")
    ;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): OrderSide {
            return when (value) {
                SELL.value -> SELL
                BUY.value -> BUY
                else -> throw IllegalArgumentException("Unsupported value '$value'")
            }
        }
    }
}