package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.Network
import com.rarible.opensea.client.SeaportProtocolClient
import com.rarible.opensea.client.SeaportProtocolClientImpl
import com.rarible.opensea.client.agent.UserAgentProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    @ConditionalOnMissingBean(SeaportProtocolClient::class)
    fun seaPortProtocolClient(): SeaportProtocolClientImpl {
        val endpoint = when (properties.network) {
            Network.ETHEREUM, Network.POLYGON -> SEA_PORT_ENDPOINT
            Network.GOERLI, Network.MUMBAI -> TESTNET_SEA_PORT_ENDPOINT
        }
        logger.info("Start Seaport client [endpoint=$endpoint], [network=${properties.network}]")
        return SeaportProtocolClientImpl(
            endpoint = endpoint,
            network = properties.network,
            apiKey = properties.apiKey,
            userAgentProvider = if (properties.changeUserAgent) userAgentProvider else null,
            proxy = properties.proxy,
            logRawJson = properties.logRawJson
        )
    }

    private companion object {
        val logger: Logger = LoggerFactory.getLogger(SeaPortProtocolClientAutoConfiguration::class.java)
        val SEA_PORT_ENDPOINT: URI = URI.create("https://api.opensea.io")
        val TESTNET_SEA_PORT_ENDPOINT: URI = URI.create("https://testnets-api.opensea.io")
    }
}
