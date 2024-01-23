package com.rarible.opensea.subscriber.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class OpenseaEventType(@get:JsonValue val value: String) {
    ITEM_CANCELLED("item_cancelled"),
    ORDER_INVALIDATED("order_invalidated"),
    ORDER_REVALIDATED("order_revalidated");

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String) = OpenseaEventType.values().find { it.value == value }
            ?: throw IllegalArgumentException("Unsupported value '$value'")
    }
}