package com.rarible.opensea.client.autoconfigure

import com.rarible.opensea.client.Network
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

internal const val RARIBLE_OPEN_SEA = "rarible.opensea"
internal const val RARIBLE_SEA_PORT_PROTOCOL = "rarible.seaport"

interface OpenSeaClientProperties {
    val proxy: URI?
    val apiKey: String?
    val changeUserAgent: Boolean
    val logRawJson: Boolean
}

@ConstructorBinding
@ConfigurationProperties(RARIBLE_OPEN_SEA)
data class LegacyOpenSeaClientProperties(
    val testnet: Boolean = false,
    override val proxy: URI? = null,
    override val apiKey: String? = null,
    override val changeUserAgent: Boolean = true,
    override val logRawJson: Boolean = false
) : OpenSeaClientProperties

@ConstructorBinding
@ConfigurationProperties(RARIBLE_SEA_PORT_PROTOCOL)
data class SeaPortProtocolClientProperties(
    val network: Network = Network.ETHEREUM,
    override val proxy: URI? = null,
    override val apiKey: String? = null,
    override val changeUserAgent: Boolean = true,
    override val logRawJson: Boolean = false
) : OpenSeaClientProperties