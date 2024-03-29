package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v2.FulfillListingRequest
import com.rarible.opensea.client.model.v2.FulfillListingResponse
import com.rarible.opensea.client.model.v2.NftsByContractRequest
import com.rarible.opensea.client.model.v2.NftsResponse
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrders
import java.net.URI
import kotlin.math.max

class SeaportProtocolClientImpl(
    endpoint: URI,
    private val network: Network,
    apiKey: String?,
    userAgentProvider: UserAgentProvider?,
    proxy: URI?,
    logRawJson: Boolean = false,
    compress: Boolean,
) : SeaportProtocolClient, AbstractOpenSeaClient(endpoint, apiKey, userAgentProvider, proxy, logRawJson, compress) {

    override suspend fun getListOrders(request: OrdersRequest): OpenSeaResult<SeaportOrders> {
        val uri = uriBuilderFactory.builder().run {
            path("/v2/orders/${network.value}/seaport/listings")
            request.limit?.let { queryParam("limit", it) }
            request.cursor?.let { queryParam("cursor", it) }
            request.token?.let { queryParam("asset_contract_address", it.toString()) }
            request.tokenIds?.let {
                it.forEach { tokenId ->
                    queryParam("token_ids", tokenId.toString())
                }
            }
            build()
        }
        return getOpenSeaResult(uri)
    }

    override suspend fun getFulfillListingInfo(request: FulfillListingRequest): OpenSeaResult<FulfillListingResponse> {
        val uri = uriBuilderFactory.builder().run {
            path("/v2/listings/fulfillment_data")
            build()
        }
        return postOpenSeaResult(uri, request.toPayload())
    }

    override suspend fun getNftByContract(request: NftsByContractRequest): OpenSeaResult<NftsResponse> {
        val uri = uriBuilderFactory.builder().run {
            path("/api/v2/chain/${(request.network ?: network).value}/contract/${request.contract.prefixed()}/nfts")
            queryParam("limit", max(request.limit, 200))
            request.next?.let { queryParam("next", it) }
            build()
        }
        return getOpenSeaResult(uri)
    }
}