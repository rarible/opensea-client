package com.rarible.opensea.client.model.v1

data class OpenSeaAssets(
    val next: String?,
    val previous: String?,
    val assets: List<Asset>
)

