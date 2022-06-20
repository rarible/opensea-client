package com.rarible.opensea.client

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.model.OpenSeaError
import com.rarible.opensea.client.model.OpenSeaErrorCode
import com.rarible.opensea.client.model.OpenSeaResult
import com.rarible.opensea.client.model.OperationResult
import io.netty.channel.ChannelOption
import io.netty.channel.epoll.EpollChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.util.unit.DataSize
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import reactor.netty.transport.ProxyProvider
import java.net.URI
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.TimeUnit

abstract class AbstractOpenSeaClient(
    endpoint: URI,
    protected val apiKey: String?,
    protected val userAgentProvider: UserAgentProvider?,
    proxy: URI?,
    protected val logRawJson: Boolean = false
) {
    protected val logger = LoggerFactory.getLogger(javaClass)

    protected val mapper = ObjectMapper().apply {
        registerModule(KotlinModule())
        registerModule(JavaTimeModule())
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    protected val transport = initTransport(endpoint, proxy)

    protected val uriBuilderFactory = DefaultUriBuilderFactory(endpoint.toASCIIString()).apply {
        encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE
    }

    protected suspend  inline fun <reified T> getOpenSeaResult(uri: URI): OpenSeaResult<T> {
        val response = transport.get()
            .uri(uri)
            .run {
                if (userAgentProvider != null) {
                    header(HttpHeaders.USER_AGENT, userAgentProvider.get())
                } else {
                    this
                }
            }
            .run {
                if (apiKey != null && apiKey.isNotBlank()) {
                    header("X-API-KEY", apiKey)
                } else {
                    this
                }
            }
            .awaitExchange { it }

        return getResult(response)
    }

    protected suspend  inline fun <reified T> getResult(response: ClientResponse): OpenSeaResult<T> {
        val httpCode = response.statusCode().value()
        val body = response.bodyToMono<ByteArray>().awaitFirstOrNull() ?: EMPTY_BODY
        return when (response.statusCode()) {
            HttpStatus.OK -> {
                if (logRawJson) {
                    logger.info("Raw OpenSea orders: ${String(body)}", )
                }
                OperationResult.success(mapper.readValue(body))
            }
            HttpStatus.NOT_FOUND -> OperationResult.fail(getError(body, httpCode, OpenSeaErrorCode.ORDERS_NOT_FOUND))
            HttpStatus.BAD_REQUEST -> OperationResult.fail(getError(body, httpCode, OpenSeaErrorCode.BAD_REQUEST))
            HttpStatus.TOO_MANY_REQUESTS -> OperationResult.fail(
                getError(
                    body,
                    httpCode,
                    OpenSeaErrorCode.TOO_MANY_REQUESTS
                )
            )
            HttpStatus.INTERNAL_SERVER_ERROR -> OperationResult.fail(
                getError(
                    body,
                    httpCode,
                    OpenSeaErrorCode.SERVER_ERROR
                )
            )
            else -> OperationResult.fail(getError(body, httpCode, OpenSeaErrorCode.UNKNOWN))
        }
    }

    protected fun getError(body: ByteArray, httpCode: Int, errorCode: OpenSeaErrorCode): OpenSeaError {
        return OpenSeaError(httpCode, errorCode, if (body.isNotEmpty()) body.toString(StandardCharsets.UTF_8) else "")
    }

    protected fun initTransport(endpoint: URI, proxy: URI?): WebClient {

        return WebClient.builder().run {
            clientConnector(clientConnector(proxy))
            exchangeStrategies(
                ExchangeStrategies.builder()
                    .codecs { it.defaultCodecs().maxInMemorySize(DEFAULT_MAX_BODY_SIZE) }
                    .build()
            )
            baseUrl(endpoint.toASCIIString())
            build()
        }
    }

    protected fun clientConnector(proxy: URI?): ClientHttpConnector {
        val provider = ConnectionProvider.builder("open-sea-connection-provider")
            .maxConnections(50)
            .pendingAcquireMaxCount(-1)
            .maxIdleTime(DEFAULT_TIMEOUT)
            .maxLifeTime(DEFAULT_TIMEOUT)
            .lifo()
            .build()

        val client = HttpClient.create(provider)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .option(EpollChannelOption.TCP_KEEPCNT, 8)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_TIMEOUT_MILLIS.toInt())
            .doOnConnected { connection ->
                connection.addHandlerLast(ReadTimeoutHandler(DEFAULT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
                connection.addHandlerLast(WriteTimeoutHandler(DEFAULT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
            }
            .responseTimeout(DEFAULT_TIMEOUT)

        val finalClient = if (proxy != null) {
            client
                .proxy { option ->
                    val userInfo = proxy.userInfo.split(":")
                    option.type(ProxyProvider.Proxy.HTTP).host(proxy.host).username(userInfo[0]).password { userInfo[1] }.port(proxy.port)
                }
        } else {
            client
        }
        return ReactorClientHttpConnector(finalClient)
    }

    protected companion object {
        val EMPTY_BODY: ByteArray = ByteArray(0)
        val DEFAULT_MAX_BODY_SIZE = DataSize.ofMegabytes(10).toBytes().toInt()
        val DEFAULT_TIMEOUT: Duration = Duration.ofSeconds(60)
        val DEFAULT_TIMEOUT_MILLIS: Long = DEFAULT_TIMEOUT.toMillis()
    }
}