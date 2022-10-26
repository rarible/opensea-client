package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.SeaportProtocolClient
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrder
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import scalether.domain.Address
import java.math.BigInteger

@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
@ActiveProfiles("local")
class OpenSeaClientAutoConfigurationTest {

    @Autowired
    private lateinit var seaPortProtocolClient: SeaportProtocolClient

    @Test
    fun `test default consumer initialized`() {
        assertNotEquals(seaPortProtocolClient, null)
    }

    @Test
    @Disabled
    fun `should get all orders in 10 pages`() = runBlocking {
        val orders = mutableListOf<SeaportOrder>()
        var cursor: String? = null
        for (i in 1..1) {
            val request = OrdersRequest(
                limit = 50,
                cursor = cursor
            )
            val result = seaPortProtocolClient.getListOrders(request).ensureSuccess()
            orders.addAll(result.orders)
            cursor = result.next
        }
        assertEquals(orders.size, 50)
        assertEquals(orders.map { it.orderHash }.toSet().size, 50)
    }

    @Test
    @Disabled
    fun `should get orders by item`() = runBlocking {
        val orders = mutableListOf<SeaportOrder>()
        var cursor: String? = null
        for (i in 1..2) {
            val result = seaPortProtocolClient.getListOrders(
                OrdersRequest(
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
