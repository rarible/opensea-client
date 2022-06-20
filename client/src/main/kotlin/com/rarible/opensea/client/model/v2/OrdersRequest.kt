package com.rarible.opensea.client.model.v2

data class OrdersRequest(
    // Number of orders to retrieve (capped at 50).
    val limit: Int?,
    // A cursor to be supplied as a query param to retrieve the next page
    val next: String?,
    // A cursor to be supplied as a query param to retrieve the previous page
    val previous: String?
) {
    init {
        require((next != null && previous != null).not()) {
            "'next' and 'previous' params can't be defined simultaneously"
        }
    }
}