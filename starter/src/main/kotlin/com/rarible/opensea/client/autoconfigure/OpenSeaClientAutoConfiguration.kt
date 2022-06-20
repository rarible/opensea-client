package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.OpenSeaClient
import com.rarible.opensea.client.OpenSeaClientImpl
import com.rarible.opensea.client.agent.UserAgentProvider
import com.rarible.opensea.client.agent.SimpleUserAgentProviderImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.net.URI

@EnableConfigurationProperties(OpenSeaClientProperties::class)
class OpenSeaClientAutoConfiguration(
    private val properties: OpenSeaClientProperties
) {
    @Bean
    @ConditionalOnMissingBean(UserAgentProvider::class)
    fun openSeaUserAgentProvider(): SimpleUserAgentProviderImpl {
        return SimpleUserAgentProviderImpl()
    }

    @Bean
    @ConditionalOnMissingBean(OpenSeaClient::class)
    fun openSeaClient(userAgentProvider: UserAgentProvider): OpenSeaClient {
        return OpenSeaClientImpl(
            endpoint = properties.endpoint ?: OPEN_SEA_ENDPOINT,
            apiKey = properties.apiKey,
            userAgentProvider = if (properties.changeUserAgent) userAgentProvider else null,
            proxy = properties.proxy,
            logRawJson = properties.logRawJson
        )
    }

    private companion object {
        val OPEN_SEA_ENDPOINT: URI = URI.create("https://api.opensea.io")
    }
}
