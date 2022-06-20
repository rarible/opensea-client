package com.rarible.opensea.client

import com.rarible.opensea.client.agent.SimpleUserAgentProviderImpl
import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.v1.OpenSeaOrder
import com.rarible.opensea.client.model.v1.OrderSide
import com.rarible.opensea.client.model.v1.OrdersRequest
import com.rarible.opensea.client.model.v1.SortBy
import com.rarible.opensea.client.model.v1.SortDirection
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.net.URI
import java.time.Instant

@Disabled
internal class LegacyOpenSeaClientTest {
    private val client = OpenSeaClientImpl(
        endpoint = URI.create("https://api.opensea.io"),
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
        val orders = mutableListOf<OpenSeaOrder>()
        val listedBefore = Instant.now()

        for (i in 1..2) {
            val request = OrdersRequest(
                limit = 50,
                offset = orders.size,
                sortBy = SortBy.CREATED_DATE,
                sortDirection = SortDirection.DESC,
                listedBefore = listedBefore,
                side = OrderSide.SELL,
                listedAfter = null
            )
            orders.addAll(client.getOrders(request).ensureSuccess().orders)
        }
        Assertions.assertEquals(orders.size, 100)
        Assertions.assertEquals(orders.map { it.orderHash }.toSet().size, 500)
    }
}
