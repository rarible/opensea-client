package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.URI

internal class SeaPortProtocolClientTest {
    private val client = SeaportProtocolClientImpl(
        endpoint = URI.create("https://api.opensea.io"),
        network = SeaportProtocolClient.Network.ETHEREUM,
        apiKey = null,
        userAgentProvider = object : UserAgentProvider {
            override fun get(): String {
                return ""
            }
        },
        proxy = null,
        logRawJson = true
    )

    @Test
    fun `should get all orders in 10 pages`() = runBlocking {
        val orders = mutableListOf<SeaportOrder>()
        var cursor: String? = null
        for (i in 1..10) {
            val request = OrdersRequest(
                limit = 50,
                cursor = cursor
            )
            val result = client.getListOrders(request).ensureSuccess()
            orders.addAll(result.orders)
            cursor = result.next
        }
        assertEquals(orders.size, 500)
        assertEquals(orders.map { it.orderHash }.toSet().size, 500)
    }
}