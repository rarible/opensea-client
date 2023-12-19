package com.rarible.opensea.client

import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v2.FulfillListingRequest
import com.rarible.opensea.client.model.v2.FulfillListingResponse
import com.rarible.opensea.client.model.v2.NftsByContractRequest
import com.rarible.opensea.client.model.v2.NftsResponse
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrders

interface SeaportProtocolClient {
    suspend fun getListOrders(request: OrdersRequest): OpenSeaResult<SeaportOrders>

    suspend fun getFulfillListingInfo(request: FulfillListingRequest): OpenSeaResult<FulfillListingResponse>

    suspend fun getNftByContract(request: NftsByContractRequest): OpenSeaResult<NftsResponse>
}
