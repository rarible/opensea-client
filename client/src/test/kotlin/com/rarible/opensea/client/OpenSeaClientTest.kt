package com.rarible.opensea.client

import com.rarible.opensea.client.model.OpenSeaOrder
import com.rarible.opensea.client.model.OrdersRequest
import com.rarible.opensea.client.model.SortBy
import com.rarible.opensea.client.model.SortDirection
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.net.URI
import java.time.Instant

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
                sortBy = SortBy.CREATED_DATE,
                sortDirection = SortDirection.DESC,
                listedAfter = null,
                listedBefore = null
            )
            orders.addAll(client.getOrders(request).ensureSuccess().orders)
        }
        Assertions.assertEquals(orders.size, 500)
        Assertions.assertEquals(orders.map { it.orderHash }.toSet().size, 500)
    }

    @Test
    fun `should get all from target listed time`() = runBlocking<Unit> {
        val orders = mutableListOf<OpenSeaOrder>()
        val listedBefore = Instant.now()
        val listedAfter = Instant.now()

        do {
            val request = OrdersRequest(
                limit = 50,
                offset = orders.size,
                sortBy = SortBy.CREATED_DATE,
                sortDirection = SortDirection.DESC,
                listedAfter = null,
                listedBefore = listedBefore
            )
            val result = client.getOrders(request).ensureSuccess().orders

            orders.addAll(result)

            result.forEach {
                println("----> id=${it.id}, ${it.createdAt}, ${it.side}, ${it.basePrice}, ${it.maker.user?.name} ${it.asset}")
            }
            println("\n\n")
        } while (result.isNotEmpty())

    }
}
