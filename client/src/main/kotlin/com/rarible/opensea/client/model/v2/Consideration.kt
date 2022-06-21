package com.rarible.opensea.client.model.v2

import java.math.BigInteger

data class Consideration(
    val item_type: Long,

    val token: String,

    val identifierOrCriteria: String,

    val startAmount: BigInteger,

    val endAmount: BigInteger,

    val recipient: String
)