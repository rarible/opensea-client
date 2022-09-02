package com.rarible.opensea.client.model.v2

data class SeaportOrders(
    val next: String?,
    val previous: String?,
    val orders: List<SeaportOrder>
)