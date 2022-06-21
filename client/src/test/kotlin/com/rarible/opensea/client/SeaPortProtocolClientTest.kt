package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaPortOrder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.URI

internal class SeaPortProtocolClientTest {
    private val client = SeaPortProtocolClientImpl(
        endpoint = URI.create("https://api.opensea.io"),
        network = SeaPortProtocolClient.Network.ETHEREUM,
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
        val orders = mutableListOf<SeaPortOrder>()
        var next: String? = null
        for (i in 1..1) {
            val request = OrdersRequest(
                limit = 50,
                next = next,
                previous = null
            )
            val result = client.getListOrders(request).ensureSuccess()
            orders.addAll(result.orders)
            next = result.next
        }
        assertEquals(orders.size, 50)
        assertEquals(orders.map { it.orderHash }.toSet().size, 50)
    }
}