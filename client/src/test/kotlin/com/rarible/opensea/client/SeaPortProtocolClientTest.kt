package com.rarible.opensea.client

import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import scalether.domain.Address
import java.math.BigInteger
import java.net.URI

@Disabled
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

    @Test
    fun `should get orders by item`() = runBlocking {
        val orders = mutableListOf<SeaportOrder>()
        var cursor: String? = null
        for (i in 1..2) {
            val result = client.getListOrders(OrdersRequest(
                null,
                cursor,
                Address.apply("0x7deb7bce4d360ebe68278dee6054b882aa62d19c"),
                listOf(BigInteger("8"))
            )
            ).ensureSuccess()
            orders.addAll(result.orders)
            cursor = result.next
        }
        assertEquals(orders.size, 37)
    }


}