package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigInteger

data class ProtocolData(
    val offerer: String,

    val zone: String,

    @JsonProperty("zone_hash")
    val zoneHash: String,

    @JsonProperty("start_time")
    val startTime: Long,

    @JsonProperty("end_time")
    val endTime: Long,

    @JsonProperty("order_type")
    val orderType: OrderType,

    val salt: BigInteger,

    val conduitKey: String,

    val nonce: Long,

    val offer: Offer,

    val consideration: Consideration
)