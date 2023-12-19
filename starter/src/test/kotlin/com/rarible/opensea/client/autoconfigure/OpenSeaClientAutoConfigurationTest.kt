package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.Network
import com.rarible.opensea.client.OpenSeaClient
import com.rarible.opensea.client.SeaportProtocolClient
import com.rarible.opensea.client.model.v1.Asset
import com.rarible.opensea.client.model.v1.AssetsRequest
import com.rarible.opensea.client.model.v1.SimpleNft
import com.rarible.opensea.client.model.v2.FulfillListingRequest
import com.rarible.opensea.client.model.v2.NftsByContractRequest
import com.rarible.opensea.client.model.v2.OrdersRequest
import com.rarible.opensea.client.model.v2.SeaportOrder
import io.daonomic.rpc.domain.Word
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    @Autowired
    private lateinit var openSeaClient: OpenSeaClient

    @Test
    fun `test default consumer initialized`() {
        assertThat(seaPortProtocolClient).isNotNull
        assertThat(openSeaClient).isNotNull
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
    fun `get orders fulfill data`() = runBlocking<Unit> {
        val request = FulfillListingRequest(
            hash = Word.apply("0x2bfa3bfcad86f563cc609f2f0081aa2c06951ae764c8df52fd796942170815db"),
            network = Network.GOERLI,
            protocolAddress = Address.apply("0x00000000000001ad428e4906aE43D8F9852d0dD6"),
            fulfiller = Address.apply("0xd5be662cf4d6d9722990ca2cdd16bf53ecb94325")
        )
        val result = seaPortProtocolClient.getFulfillListingInfo(request).ensureSuccess()
        assertThat(result).isNotNull
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

    @Test
    @Disabled
    fun `get assets`() = runBlocking {
        val assets = mutableSetOf<Asset>()
        var cursor: String? = null
        do {
            val request = AssetsRequest(
                contracts = listOf(Address.apply("0x10f1d490b98b65cf5ad8babab840a979a49c9b84")),
                tokenIds = emptyList(),
                limit = 50,
                cursor = cursor
            )
            val result = openSeaClient.getAssets(request).ensureSuccess()
            assets.addAll(result.assets)
            cursor = result.next
            logger.info("GET ${result.assets.size} assets, total=${assets.size}")
        } while (cursor != null && result.assets.size >= 50)

        logger.info("Fount ${assets.size} assets")
    }

    @Test
    fun `get nfts by contract`() = runBlocking {
        val nfts = mutableSetOf<SimpleNft>()
        var next: String? = null
        do {
            val request = NftsByContractRequest(
                contract = Address.apply("0x3f705da685dbae723850951c9081f545052d1ad2"),
                network = Network.ETHEREUM,
                limit = 50,
                next = next
            )
            val result = seaPortProtocolClient.getNftByContract(request).ensureSuccess()
            next = result.next
            nfts.addAll(result.nfts)
        } while (next != null && result.nfts.size >= 50)

        logger.info("Fount ${nfts.size} assets")
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(OpenSeaClientAutoConfigurationTest::class.java)
    }
}
