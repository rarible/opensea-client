package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty
import com.rarible.opensea.client.Network
import io.daonomic.rpc.domain.Word
import scalether.domain.Address

data class FulfillListingRequest(
    val hash: Word,
    val network: Network,
    val protocolAddress: Word,
    val fulfiller: Address
) {
    internal fun toPayload(): Payload {
        return Payload(
            listing = Listing(
                hash = hash,
                chain = network.value,
                protocolAddress = protocolAddress
            ),
            fulfiller = Fulfiller(
                address = fulfiller
            )
        )
    }

    internal data class Payload(
        val listing: Listing,
        val fulfiller: Fulfiller
    )

    internal data class Listing(
        val hash: Word,
        val chain: String,
        @JsonProperty("protocol_address")
        val protocolAddress: Word
    )

    internal data class Fulfiller(
        val address: Address
    )
}