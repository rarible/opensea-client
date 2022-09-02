package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrders
import scalether.domain.Address
import java.math.BigInteger
import java.net.URI

class SeaportProtocolClientImpl(
    endpoint: URI,
    private val network: SeaportProtocolClient.Network,
    apiKey: String?,
    userAgentProvider: UserAgentProvider?,
    proxy: URI?,
    logRawJson: Boolean = false
) : SeaportProtocolClient, AbstractOpenSeaClient(endpoint, apiKey, userAgentProvider, proxy, logRawJson) {

    override suspend fun getListOrders(request: OrdersRequest): OpenSeaResult<SeaportOrders> {
        val uri = uriBuilderFactory.builder().run {
            path("/v2/orders/${network.value}/seaport/listings")
            request.limit?.let { queryParam("limit", it) }
            request.cursor?.let { queryParam("cursor", it) }
            build()
        }
        return getOpenSeaResult(uri)
    }

    override suspend fun getListOrders(contract: Address, tokenId: BigInteger, cursor: String?): OpenSeaResult<SeaportOrders> {
        val uri = uriBuilderFactory.builder().run {
            path("/v2/orders/${network.value}/seaport/listings")
            queryParam("asset_contract_address", contract.toString())
            queryParam("token_ids", tokenId.toString())
            cursor?.let { queryParam("cursor", it) }
            build()
        }
        return getOpenSeaResult(uri)
    }
}