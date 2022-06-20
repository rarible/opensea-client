package com.rarible.opensea.client.model.v2

data class SeaPortOrders(
    val next: String?,
    val previous: String?,
    val orders: List<SeaPortOrder>
)

