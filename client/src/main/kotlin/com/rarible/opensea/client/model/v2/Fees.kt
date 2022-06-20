package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class Fees(
    val account: Account,

    @JsonProperty("basis_points")
    val basisPoints: Long
)