package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v1.AssetsRequest
import com.rarible.opensea.client.model.v1.OpenSeaAssets
import com.rarible.opensea.client.model.v1.OpenSeaOrderItems
import com.rarible.opensea.client.model.v1.OrdersRequest
import java.net.URI

class OpenSeaClientImpl(
    endpoint: URI,
    apiKey: String?,
    userAgentProvider: UserAgentProvider?,
    proxy: URI?,
    logRawJson: Boolean = false
) : OpenSeaClient, AbstractOpenSeaClient(endpoint, apiKey, userAgentProvider, proxy, logRawJson) {

    @Deprecated("Should use SeaPortProtocolClientImpl to get orders")
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

    override suspend fun getAssets(request: AssetsRequest): OpenSeaResult<OpenSeaAssets> {
        val uri = uriBuilderFactory.builder().run {
            path("/api/v1/assets")
            when (request.contracts.size) {
                0 -> {}
                1 -> queryParam("asset_contract_address", request.contracts.single())
                else -> queryParam("asset_contract_addresses", request.contracts)
            }
            if (request.tokenIds.isNotEmpty()) {
                queryParam("token_ids", request.tokenIds)
            }
            request.includeOrders?.let { queryParam("include_orders", it) }
            request.sortDirection?.let { queryParam("order_direction", it.value) }
            request.limit?.let { queryParam("limit", it) }
            request.cursor?.let { queryParam("cursor", it) }
            build()
        }
        return getOpenSeaResult(uri)
    }
}