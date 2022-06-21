package com.rarible.opensea.client.model.v2

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

    val zoneHash: Word,

    val salt: BigInteger,

    val conduitKey: Word,

    val totalOriginalConsiderationItems: BigInteger,

    val counter: BigInteger
)