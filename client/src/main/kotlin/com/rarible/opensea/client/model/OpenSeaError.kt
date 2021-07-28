package com.rarible.opensea.client.model

data class OpenSeaError(
    val httpCode: Int,
    val code: OpenSeaErrorCode,
    val message: String
)
