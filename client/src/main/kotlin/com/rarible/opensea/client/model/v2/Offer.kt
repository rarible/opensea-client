package com.rarible.opensea.client.model.v2

import scalether.domain.Address
import java.math.BigInteger

data class Offer(
    val itemType: ItemType,

    val token: Address,

    val identifierOrCriteria: BigInteger,

    val startAmount: BigInteger,

    val endAmount: BigInteger
)