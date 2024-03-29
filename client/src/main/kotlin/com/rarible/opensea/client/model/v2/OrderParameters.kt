package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.rarible.opensea.client.deserializer.OpenSeaCounterDeserializer
import com.rarible.opensea.client.deserializer.OpenSeaWordDeserializer
import com.rarible.opensea.client.deserializer.SaltDeserializer
import io.daonomic.rpc.domain.Word
import scalether.domain.Address
import java.math.BigInteger

data class OrderParameters(
    val offerer: Address,

    val zone: Address,

    val offer: List<Offer>,

    val consideration: List<Consideration>,

    val orderType: OrderType,

    val startTime: BigInteger,

    val endTime: BigInteger,

    @JsonDeserialize(using = OpenSeaWordDeserializer::class)
    val zoneHash: Word,

    @JsonDeserialize(using = SaltDeserializer::class)
    val salt: BigInteger,

    @JsonDeserialize(using = OpenSeaWordDeserializer::class)
    val conduitKey: Word,

    val totalOriginalConsiderationItems: BigInteger,

    @JsonDeserialize(using = OpenSeaCounterDeserializer::class)
    val counter: BigInteger
)