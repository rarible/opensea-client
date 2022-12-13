package com.rarible.opensea.client

import com.rarible.opensea.client.model.v1.OpenSeaOrderItems
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v1.AssetsRequest
import com.rarible.opensea.client.model.v1.OpenSeaAssets
import com.rarible.opensea.client.model.v1.OrdersRequest

interface OpenSeaClient {
    @Deprecated("Should use SeaPortProtocolClientImpl to get orders")
    suspend fun getOrders(request: OrdersRequest): OpenSeaResult<OpenSeaOrderItems>

    suspend fun getAssets(request: AssetsRequest): OpenSeaResult<OpenSeaAssets>
}

