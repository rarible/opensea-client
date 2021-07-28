package com.rarible.opensea.client.model

import java.time.Instant

data class OrdersRequest(
    // Only show orders listed after this timestamp. Seconds since the Unix epoch
    val listedAfter: Instant?,
    // Only show orders listed before this timestamp. Seconds since the Unix epoch.
    val listedBefore: Instant?,
    // Number of orders to return (capped at 50).
    val limit: Int?,
    // Number of orders to offset by (for pagination)
    val offset: Int?
)
