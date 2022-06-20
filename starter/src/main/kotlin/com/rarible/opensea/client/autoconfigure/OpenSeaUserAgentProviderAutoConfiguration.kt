package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.agent.SimpleUserAgentProviderImpl
import com.rarible.opensea.client.agent.UserAgentProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

class OpenSeaUserAgentProviderAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(UserAgentProvider::class)
    fun openSeaUserAgentProvider(): SimpleUserAgentProviderImpl {
        return SimpleUserAgentProviderImpl()
    }
}