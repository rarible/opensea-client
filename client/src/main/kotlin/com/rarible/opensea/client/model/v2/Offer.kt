package com.rarible.opensea.client.model.v2

import java.math.BigInteger

data class Offer(
    val itemType: Long,

    val token: String,

    val identifierOrCriteria: String,

    val startAmount: BigInteger,

    val endAmount: BigInteger
)