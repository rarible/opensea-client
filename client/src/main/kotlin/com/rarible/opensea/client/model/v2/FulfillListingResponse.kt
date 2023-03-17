package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class FulfillListingResponse(
    @JsonProperty("fulfillment_data")
    val fulfillmentData: FulfillmentData,
)