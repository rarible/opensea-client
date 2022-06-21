package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import java.math.BigInteger

data class OrderParameters(
    val offerer: String,

    val zone: String,

    val zoneHash: String,

    val startTime: Long,

    val endTime: Long,

    val orderType: OrderType,

    val salt: BigInteger,

    val conduitKey: String,

    val nonce: Long,

    val offer: List<Offer>,

    val consideration: List<Consideration>
) {
    enum class OrderType(@get:JsonValue val value: Int) {
        BUY(1),
        SELL(2),
        BUNDLE(3)
        ;

        companion object {
            @JsonCreator
            @JvmStatic
            fun fromValue(value: Int): OrderType {
                return when (value) {
                    BUY.value -> BUY
                    SELL.value -> SELL
                    BUNDLE.value -> BUNDLE
                    else -> throw IllegalArgumentException("Unsupported value '$value'")
                }
            }
        }
    }
}