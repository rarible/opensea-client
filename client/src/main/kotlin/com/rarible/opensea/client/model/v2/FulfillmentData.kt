package com.rarible.opensea.client.model.v2

data class FulfillmentData(
    val transaction: FulfillmentTransaction,
    val orders: List<FulfilmentOrder>
)