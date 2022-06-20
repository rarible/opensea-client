package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.SeaPortProtocolClient
import com.rarible.opensea.client.SeaPortProtocolClientImpl
import com.rarible.opensea.client.agent.UserAgentProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.net.URI

@EnableConfigurationProperties(SeaPortProtocolClientProperties::class)
class SeaPortProtocolClientAutoConfiguration(
    private val properties: SeaPortProtocolClientProperties,
    private val userAgentProvider: UserAgentProvider
) {
    @Bean
    @ConditionalOnMissingBean(SeaPortProtocolClient::class)
    fun seaPortProtocolClient(): SeaPortProtocolClientImpl {
        return SeaPortProtocolClientImpl(
            endpoint = properties.endpoint ?: if (properties.testnet) TESTNET_SEA_PORT_ENDPOINT else SEA_PORT_ENDPOINT,
            network = if (properties.testnet) SeaPortProtocolClient.Network.RINKEBY else SeaPortProtocolClient.Network.ETHEREUM,
            apiKey = properties.apiKey,
            userAgentProvider = if (properties.changeUserAgent) userAgentProvider else null,
            proxy = properties.proxy,
            logRawJson = properties.logRawJson
        )
    }

    private companion object {
        val SEA_PORT_ENDPOINT: URI = URI.create("https://api.opensea.io")
        val TESTNET_SEA_PORT_ENDPOINT: URI = URI.create("https://testnets-api.opensea.io")
    }
}
