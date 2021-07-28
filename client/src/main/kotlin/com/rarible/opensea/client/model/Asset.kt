package com.rarible.opensea.client.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigInteger

data class Asset(
    val id: BigInteger,
    @JsonProperty("token_id")
    val tokenId: BigInteger,
    @JsonProperty("asset_contract")
    val assetContract: AssetContract
)
