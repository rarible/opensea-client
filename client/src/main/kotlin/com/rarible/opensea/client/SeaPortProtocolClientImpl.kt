package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaPortOrders
import java.net.URI

class SeaPortProtocolClientImpl(
    endpoint: URI,
    apiKey: String?,
    userAgentProvider: UserAgentProvider?,
    proxy: URI?,
    logRawJson: Boolean = false
) : SeaPortProtocolClient, AbstractOpenSeaClient(endpoint, apiKey, userAgentProvider, proxy, logRawJson) {

    override suspend fun getListOrders(request: OrdersRequest): OpenSeaResult<SeaPortOrders> {
        TODO("Not yet implemented")
    }
}