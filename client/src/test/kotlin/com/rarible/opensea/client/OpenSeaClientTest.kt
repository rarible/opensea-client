package com.rarible.opensea.client

import com.rarible.opensea.client.model.*
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
        val listedBefore = Instant.now()

        for (i in 1..50) {
            val request = OrdersRequest(
                limit = 10,
                offset = orders.size,
                sortBy = SortBy.CREATED_DATE,
                sortDirection = SortDirection.DESC,
                listedBefore = listedBefore,
                side = null,
                listedAfter = null
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

        do {
            val request = OrdersRequest(
                limit = 50,
                offset = orders.size,
                side = OrderSide.SELL,
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
            println("\n${orders.size}")
            println("\n\n")
        } while (result.isNotEmpty())

    }
}
