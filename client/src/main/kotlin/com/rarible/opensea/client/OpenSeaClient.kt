package com.rarible.opensea.client

import com.rarible.opensea.client.model.v1.OpenSeaOrderItems
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v1.OrdersRequest

@Deprecated("Should use SeaPortProtocolClient")
interface OpenSeaClient {
    suspend fun getOrders(request: OrdersRequest): OpenSeaResult<OpenSeaOrderItems>
}

