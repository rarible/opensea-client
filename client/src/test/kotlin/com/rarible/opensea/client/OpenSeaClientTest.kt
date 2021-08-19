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
    private val client = OpenSeaClient(URI.create("https://api.opensea.io"), null)

    @Test
    fun `should get all orders in 10 pages`() = runBlocking {
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

        run {
            val start = Instant.now().epochSecond + 1
            val listedAfter1 = Instant.ofEpochSecond(start) //Instant.now() - Duration.ofSeconds(2)
            val listedBefore1 = Instant.ofEpochSecond(start + 2) //Instant.now() - Duration.ofSeconds(1)
            println("\nstart=${listedAfter1.epochSecond}, end=${listedBefore1.epochSecond}")

            val orders1 = getOrders(listedAfter1, listedBefore1, 200)
            println("\nstart=${listedAfter1.epochSecond}, end=${listedBefore1.epochSecond}")
            println("minListingTime=${orders1.map { it.listingTime }.min()}, maxListingTime=${orders1.map { it.listingTime }.max()}")
            println("minCreate=${orders1.map { it.createdAt.epochSecond }.min()}, maxCreate=${orders1.map { it.createdAt.epochSecond }.max()}")
            println("size=${orders1.size}")
        }
//        run {
//            val listedAfter1 = Instant.ofEpochSecond(1628483150) //Instant.now() - Duration.ofSeconds(2)
//            val listedBefore1 = Instant.ofEpochSecond(1628483160) //Instant.now() - Duration.ofSeconds(1)
//            println("\nstart=${listedAfter1.epochSecond}, end=${listedBefore1.epochSecond}")
//
//            val orders1 = getOrders(listedAfter1, listedBefore1, 200)
//            println("\nstart=${listedAfter1.epochSecond}, end=${listedBefore1.epochSecond}")
//            println("minListingTime=${orders1.map { it.listingTime }.min()}, maxListingTime=${orders1.map { it.listingTime }.max()}")
//            println("minCreate=${orders1.map { it.createdAt.epochSecond }.min()}, maxCreate=${orders1.map { it.createdAt.epochSecond }.max()}")
//            println("size=${orders1.size}")
//        }

//
//        println("\nstart=${listedAfter1.epochSecond}")
//        println("minListingTime=${orders1.map { it.listingTime }.min()}, maxListingTime=${orders1.map { it.listingTime }.max()}")
//
//        delay(Duration.ofSeconds(2).toMillis())
//        println("\n\n---->next)\n\n")
//
//        val listedAfter2 = Instant.ofEpochSecond(orders1.map { it.listingTime }.max()!!)
//        val order2 = getOrders(listedAfter2, 200)
//
//        println("\nstart=${listedAfter2.epochSecond}")
//        println("minListingTime=${order2.map { it.listingTime }.min()}, maxListingTime=${order2.map { it.listingTime }.max()}")
    }

    private suspend fun getOrders(listedAfter: Instant?, listedBefore: Instant?, max: Int): List<OpenSeaOrder>  {
        val orders = mutableListOf<OpenSeaOrder>()

        do {
            val request = OrdersRequest(
                limit = 50,
                offset = orders.size,
                side = null,
                sortBy = SortBy.CREATED_DATE,
                sortDirection = SortDirection.ASC,
                listedAfter = listedAfter,
                listedBefore = listedBefore
            )
            val result = client.getOrders(request).ensureSuccess().orders
            result.forEach {
                println("----> id=${it.id}, ${it.listingTime} ${it.createdAt}, ${it.side}, ${it.basePrice}, ${it.maker.user?.name} ${it.asset}")
            }
            println("\n\n")

            orders.addAll(result)
        } while (result.isNotEmpty())

        return orders
    }
}
