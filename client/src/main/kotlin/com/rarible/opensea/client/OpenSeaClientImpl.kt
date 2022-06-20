package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v1.OpenSeaOrderItems
import com.rarible.opensea.client.model.v1.OrdersRequest
import java.net.URI

@Deprecated("Should use SeaPortProtocolClientImpl")
class OpenSeaClientImpl(
    endpoint: URI,
    apiKey: String?,
    userAgentProvider: UserAgentProvider?,
    proxy: URI?,
    logRawJson: Boolean = false
) : OpenSeaClient, AbstractOpenSeaClient(endpoint, apiKey, userAgentProvider, proxy, logRawJson) {

    override suspend fun getOrders(request: OrdersRequest): OpenSeaResult<OpenSeaOrderItems> {
        val uri = uriBuilderFactory.builder().run {
            path("/wyvern/v1/orders")
            queryParam("bundled", "false")
            queryParam("include_bundled", "false")
            queryParam("include_invalid", "false")
            request.limit?.let { queryParam("limit", it) }
            request.offset?.let { queryParam("offset", it) }
            request.listedAfter?.let { queryParam("listed_after", it.epochSecond) }
            request.listedBefore?.let { queryParam("listed_before", it.epochSecond) }
            request.sortBy?.let { queryParam("order_by", it.value) }
            request.sortDirection?.let { queryParam("order_direction", it.value) }
            request.side?.let { queryParam("side", it.value) }
            build()
        }
        return getOpenSeaResult(uri)
    }
}