package com.rarible.opensea.client.model.v1

import scalether.domain.Address
import java.math.BigInteger

data class AssetsRequest(
    val contracts: List<Address>,
    val tokenIds: List<BigInteger>,
    val cursor: String? = null,
    val sortDirection: SortDirection? = null,
    val includeOrders: Boolean? = null,
    val limit: Int? = null,
)
