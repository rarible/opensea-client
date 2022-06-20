package com.rarible.opensea.client

import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaPortOrders

interface SeaPortProtocolClient {
    suspend fun getListOrders(request: OrdersRequest): OpenSeaResult<SeaPortOrders>

    enum class Network(val value: String) {
        RINKEBY("rinkeby"),
        ETHEREUM("ethereum")
    }
}