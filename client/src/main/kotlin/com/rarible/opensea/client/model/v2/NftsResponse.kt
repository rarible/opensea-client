package com.rarible.opensea.client.model.v2

import com.rarible.opensea.client.model.v1.SimpleNft

data class NftsResponse(
    val nfts: List<SimpleNft>,
    val next: String?
)