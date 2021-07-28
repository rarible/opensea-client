package com.rarible.opensea.client

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.opensea.client.model.*
import io.netty.channel.ChannelOption
import io.netty.channel.epoll.EpollChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.util.unit.DataSize
import org.springframework.web.reactive.function.client.*
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.net.URI
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.TimeUnit

class OpenSeaClient(endpoint: URI) {
    private val mapper = ObjectMapper().apply {
        registerModule(KotlinModule())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
    private val uriBuilderFactory = DefaultUriBuilderFactory(endpoint.toASCIIString()).apply {
        encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE
    }
    private val transport = initTransport(endpoint)

    suspend fun getOrders(request: OrdersRequest): OpenSeaResult<OpenSeaOrderItems> {
        val uri = uriBuilderFactory.builder().run {
            path("/wyvern/v1/orders")
            queryParam("bundled", "false")
            queryParam("include_bundled", "false")
            queryParam("include_invalid", "false")
            queryParam("include_invalid", "false")
            request.limit?.let { queryParam("limit", it) }
            request.offset?.let { queryParam("offset", it) }
            request.listedAfter?.let { queryParam("listed_after", it.epochSecond) }
            request.listedBefore?.let { queryParam("listed_before", it.epochSecond) }
            build()
        }
        val response = transport.get()
            .uri(uri)
            .awaitExchange()

        return getResult(response)
    }

    private fun clientConnector(): ClientHttpConnector {
        val provider = ConnectionProvider.builder("open-sea-connection-provider")
            .maxConnections(50)
            .pendingAcquireMaxCount(-1)
            .maxIdleTime(DEFAULT_TIMEOUT)
            .maxLifeTime(DEFAULT_TIMEOUT)
            .lifo()
            .build()

        val client = HttpClient
            .create(provider)
            .tcpConfiguration {
                it.option(ChannelOption.SO_KEEPALIVE, true)
                    .option(EpollChannelOption.TCP_KEEPIDLE, 300)
                    .option(EpollChannelOption.TCP_KEEPINTVL, 60)
                    .option(EpollChannelOption.TCP_KEEPCNT, 8)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_TIMEOUT_MILLIS.toInt())
                    .doOnConnected { connection ->
                        connection.addHandlerLast(ReadTimeoutHandler(DEFAULT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
                        connection.addHandlerLast(WriteTimeoutHandler(DEFAULT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
                    }
            }
            .responseTimeout(DEFAULT_TIMEOUT)

        return ReactorClientHttpConnector(client)
    }

    private suspend  inline fun <reified T> getResult(response: ClientResponse): OpenSeaResult<T> {
        val httpCode = response.statusCode().value()
        val body = response.bodyToMono<ByteArray>().awaitFirstOrNull() ?: EMPTY_BODY

        return when (response.statusCode()) {
            HttpStatus.OK -> OpenSeaResult.success(mapper.readValue(body))
            HttpStatus.NOT_FOUND -> OpenSeaResult.fail(getError(body, httpCode, OpenSeaErrorCode.ORDERS_NOT_FOUND))
            HttpStatus.BAD_REQUEST -> OpenSeaResult.fail(getError(body, httpCode, OpenSeaErrorCode.BAD_REQUEST))
            HttpStatus.TOO_MANY_REQUESTS -> OpenSeaResult.fail(getError(body, httpCode, OpenSeaErrorCode.TOO_MANY_REQUESTS))
            HttpStatus.INTERNAL_SERVER_ERROR -> OpenSeaResult.fail(getError(body, httpCode, OpenSeaErrorCode.SERVER_ERROR))
            else -> OpenSeaResult.fail(getError(body, httpCode, OpenSeaErrorCode.UNKNOWN))
        }
    }

    private fun getError(body: ByteArray, httpCode: Int, errorCode: OpenSeaErrorCode): OpenSeaError {
        return OpenSeaError(httpCode, errorCode, if (body.isNotEmpty()) body.toString(StandardCharsets.UTF_8) else "")
    }

    private fun initTransport(endpoint: URI?): WebClient {
        return WebClient.builder().run {
            clientConnector(clientConnector())
            exchangeStrategies(
                ExchangeStrategies.builder()
                    .codecs { it.defaultCodecs().maxInMemorySize(DEFAULT_MAX_BODY_SIZE) }
                    .build()
            )
            endpoint?.let { baseUrl(it.toASCIIString()) }
            build()
        }
    }

    private companion object {
        val EMPTY_BODY: ByteArray = ByteArray(0)
        val DEFAULT_MAX_BODY_SIZE = DataSize.ofMegabytes(2).toBytes().toInt()
        val DEFAULT_TIMEOUT: Duration = Duration.ofSeconds(60)
        val DEFAULT_TIMEOUT_MILLIS: Long = DEFAULT_TIMEOUT.toMillis()
    }
}
