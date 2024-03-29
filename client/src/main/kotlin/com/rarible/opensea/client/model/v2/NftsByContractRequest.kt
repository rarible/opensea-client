package com.rarible.opensea.client.model.v2

import com.rarible.opensea.client.Network
import scalether.domain.Address

data class NftsByContractRequest(
    val contract: Address,
    val limit: Int,
    val next: String?,
    val network: Network? = null,
)