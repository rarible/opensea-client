package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.OpenSeaClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.net.URI

@EnableConfigurationProperties(OpenSeaClientProperties::class)
class OpenSeaClientAutoConfiguration(
    private val properties: OpenSeaClientProperties
) {
    @Bean
    @ConditionalOnMissingBean(OpenSeaClient::class)
    fun openSeaClient(): OpenSeaClient {
        return OpenSeaClient(properties.endpoint ?: OPEN_SEA_ENDPOINT)
    }

    private companion object {
        val OPEN_SEA_ENDPOINT: URI = URI.create("https://api.opensea.io")
    }
}
