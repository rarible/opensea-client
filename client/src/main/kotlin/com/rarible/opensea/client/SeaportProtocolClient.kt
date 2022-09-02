package com.rarible.opensea.client

import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrders
import scalether.domain.Address
import java.math.BigInteger

interface SeaportProtocolClient {
    suspend fun getListOrders(request: OrdersRequest): OpenSeaResult<SeaportOrders>

    /**
     * Get listings by item
     */
    suspend fun getListOrders(contract: Address, tokenId: BigInteger, cursor: String?) : OpenSeaResult<SeaportOrders>

    enum class Network(val value: String) {
        RINKEBY("rinkeby"),
        ETHEREUM("ethereum")
    }
}