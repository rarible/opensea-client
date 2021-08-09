package com.rarible.opensea.client.model

import java.time.Instant

data class OrdersRequest(
    // Only show orders listed after this timestamp. Seconds since the Unix epoch
    val listedAfter: Instant?,
    // Only show orders listed before this timestamp. Seconds since the Unix epoch.
    val listedBefore: Instant?,
    // Filter by the side of the order
    val side: OrderSide?,
    // How to sort the orders. Can be created_date for when they were made, or eth_price to see the lowest-priced orders first (converted to their ETH values). eth_price is only supported when asset_contract_address and token_id are also defined.
    val sortBy: SortBy?,
    // Can be asc or desc for ascending or descending sort. For example, to see the cheapest orders, do order_direction asc and order_by eth_price.
    val sortDirection: SortDirection?,
    // Number of orders to return (capped at 50).
    val limit: Int?,
    // Number of orders to offset by (for pagination)
    val offset: Int?
)
