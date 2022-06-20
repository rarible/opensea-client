package com.rarible.opensea.client.model.v2

data class Consideration(
    val item_type: Long,

    val token: String,

    val identifierOrCriteria: String,

    val startAmount: Long,

    val endAmount: Long,

    val recipient: String
)