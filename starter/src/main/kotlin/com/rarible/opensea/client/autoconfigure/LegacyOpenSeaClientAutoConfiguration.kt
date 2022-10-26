package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.OpenSeaClient
import com.rarible.opensea.client.OpenSeaClientImpl
import com.rarible.opensea.client.agent.UserAgentProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.net.URI

@EnableConfigurationProperties(LegacyOpenSeaClientProperties::class)
class LegacyOpenSeaClientAutoConfiguration(
    private val properties: LegacyOpenSeaClientProperties,
    private val userAgentProvider: UserAgentProvider
) {
    @Bean
    @ConditionalOnMissingBean(OpenSeaClient::class)
    fun legacyOpenSeaClient(): OpenSeaClient {
        return OpenSeaClientImpl(
            endpoint = if (properties.testnet) TESTNET_OPEN_SEA_ENDPOINT else OPEN_SEA_ENDPOINT,
            apiKey = properties.apiKey,
            userAgentProvider = if (properties.changeUserAgent) userAgentProvider else null,
            proxy = properties.proxy,
            logRawJson = properties.logRawJson
        )
    }

    private companion object {
        val OPEN_SEA_ENDPOINT: URI = URI.create("https://api.opensea.io")
        val TESTNET_OPEN_SEA_ENDPOINT: URI = URI.create("https://rinkeby-api.opensea.io")
    }
}