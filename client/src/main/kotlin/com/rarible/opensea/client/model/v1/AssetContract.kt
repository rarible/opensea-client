package com.rarible.opensea.client.model.v1

import com.fasterxml.jackson.annotation.JsonProperty
import scalether.domain.Address

data class AssetContract(
    val address: Address,
    @JsonProperty("schema_name")
    val schemaName: AssetSchema?
)
