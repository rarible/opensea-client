package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class Offer(
    @JsonProperty("offer_item")
    val offerItem: OfferItem
)