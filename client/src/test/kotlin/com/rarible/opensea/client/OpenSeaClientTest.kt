package com.rarible.opensea.client

import com.rarible.opensea.client.model.OpenSeaOrder
import com.rarible.opensea.client.model.OrdersRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.net.URI

@Disabled
internal class OpenSeaClientTest {
    private val client = OpenSeaClient(URI.create("https://api.opensea.io"))

    @Test
    fun `should get all orders in 10 pages`() = runBlocking<Unit> {
        val orders = mutableListOf<OpenSeaOrder>()

        for (i in 1..50) {
            val request = OrdersRequest(
                limit = 10,
                offset = orders.size,
                listedAfter = null,
                listedBefore = null
            )
            orders.addAll(client.getOrders(request).ensureSuccess().orders)
        }
        Assertions.assertEquals(orders.size, 500)
        Assertions.assertEquals(orders.map { it.orderHash }.toSet().size, 500)
    }
}
