package com.rarible.opensea.client.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

internal const val RARIBLE_OPEN_SEA = "rarible.opensea"
internal const val RARIBLE_SEA_PORT_PROTOCOL = "rarible.seaport"

interface OpenSeaClientProperties {
    val testnet: Boolean
    val endpoint: URI?
    val proxy: URI?
    val apiKey: String?
    val changeUserAgent: Boolean
    val logRawJson: Boolean
}

@ConstructorBinding
@ConfigurationProperties(RARIBLE_OPEN_SEA)
data class LegacyOpenSeaClientProperties(
    override val testnet: Boolean = false,
    override val endpoint: URI? = null,
    override val proxy: URI? = null,
    override val apiKey: String? = null,
    override val changeUserAgent: Boolean = true,
    override val logRawJson: Boolean = false
) : OpenSeaClientProperties

@ConstructorBinding
@ConfigurationProperties(RARIBLE_SEA_PORT_PROTOCOL)
data class SeaPortProtocolClientProperties(
    override val testnet: Boolean = false,
    override val endpoint: URI? = null,
    override val proxy: URI? = null,
    override val apiKey: String? = null,
    override val changeUserAgent: Boolean = true,
    override val logRawJson: Boolean = false
) : OpenSeaClientProperties