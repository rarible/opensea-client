package com.rarible.opensea.client.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

internal const val RARIBLE_OPEN_SEA = "rarible.opensea"

@ConstructorBinding
@ConfigurationProperties(RARIBLE_OPEN_SEA)
data class OpenSeaClientProperties(
    val endpoint: URI? = null,
    val proxy: URI? = null,
    val apiKey: String? = null,
    val changeUserAgent: Boolean = true
)
