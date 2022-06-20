package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonProperty

data class Consideration(
    @JsonProperty("consideration_item")
    val considerationItem: ConsiderationItem
)