package com.rarible.opensea.client.model.v2

import scalether.domain.Address
import java.math.BigInteger

sealed class SeaportItem {
    abstract val itemType: ItemType

    abstract val token: Address

    abstract val identifierOrCriteria: BigInteger

    abstract val startAmount: BigInteger

    abstract val endAmount: BigInteger
}

data class Offer(
    override val itemType: ItemType,

    override val token: Address,

    override val identifierOrCriteria: BigInteger,

    override val startAmount: BigInteger,

    override val endAmount: BigInteger
) : SeaportItem()

data class Consideration(
    override val itemType: ItemType,

    override val token: Address,

    override val identifierOrCriteria: BigInteger,

    override val startAmount: BigInteger,

    override val endAmount: BigInteger,

    val recipient: Address
) : SeaportItem()

