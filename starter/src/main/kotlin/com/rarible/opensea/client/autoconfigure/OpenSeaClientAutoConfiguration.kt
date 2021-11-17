package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.OpenSeaClient
import com.rarible.opensea.client.agent.UserAgentGenerator
import com.rarible.opensea.client.agent.UserAgentGeneratorImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.net.URI

@EnableConfigurationProperties(OpenSeaClientProperties::class)
class OpenSeaClientAutoConfiguration(
    private val properties: OpenSeaClientProperties
) {
    @Bean
    @ConditionalOnMissingBean
    fun openSeaUserAgentGenerator(): UserAgentGenerator {
        return UserAgentGeneratorImpl()
    }

    @Bean
    @ConditionalOnMissingBean(OpenSeaClient::class)
    fun openSeaClient(userAgentGenerator: UserAgentGenerator): OpenSeaClient {
        return OpenSeaClient(properties.endpoint ?: OPEN_SEA_ENDPOINT, properties.proxy, userAgentGenerator)
    }

    private companion object {
        val OPEN_SEA_ENDPOINT: URI = URI.create("https://api.opensea.io")
    }
}
