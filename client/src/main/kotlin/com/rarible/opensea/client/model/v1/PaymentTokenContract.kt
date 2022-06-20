package com.rarible.opensea.client.model.v1

import scalether.domain.Address
import java.math.BigInteger

data class PaymentTokenContract(
    val id: BigInteger?,
    val symbol: String,
    val address: Address,
    val decimals: BigInteger
)
