package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class OfferItem(
    @JsonProperty("item_type")
    val itemType: Long,

    val token: String,

    @JsonProperty("identifier_or_criteria")
    val identifierOrCriteria: String,

    val startAmount: Long,

    val endAmount: Long
)