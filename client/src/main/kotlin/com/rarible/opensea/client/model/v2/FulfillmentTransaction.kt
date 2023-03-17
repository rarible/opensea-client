package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class FulfillmentTransaction(
    @JsonProperty("input_data")
    val inputData: FulfillmentInputData
)