package com.rarible.opensea.client.model.v1

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import scalether.domain.Address
import java.math.BigInteger
import java.time.Instant

data class SimpleNft(
    val identifier: BigInteger,
    val collection: String?,
    val contract: Address,
    @JsonProperty("token_standard")
    val tokenStandard: String,
    val name: String?,
    val description: String?,
    @JsonProperty("image_url")
    val imageUrl: String?,
    @JsonProperty("metadata_url")
    val metadataUrl: String?,
    @JsonProperty("opensea_url")
    val openseaUrl: String,
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][z]", timezone = "UTC")
    val createdAt: Instant?,
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][z]", timezone = "UTC")
    val updatedAt: Instant?,
    @JsonProperty("is_disabled")
    val isDisabled: Boolean,
    @JsonProperty("is_nsfw")
    val isNsfw: Boolean
)