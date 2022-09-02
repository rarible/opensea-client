package com.rarible.opensea.client.model.v2

import scalether.domain.Address
import java.math.BigInteger

data class OrdersRequest(
    // Number of orders to retrieve (capped at 50).
    val limit: Int?,
    // A cursor to be supplied as a query param to retrieve the next page
    val cursor: String?,

    val token: Address? = null,

    val tokenIds: List<BigInteger>? = null
)