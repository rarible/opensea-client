package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class ConsiderationItem(
    @JsonProperty("item_type")
    val item_type: Long,

    val token: String,

    @JsonProperty("identifier_or_criteria")
    val identifierOrCriteria: String,

    val startAmount: Long,

    val endAmount: Long,

    val recipient: String
)