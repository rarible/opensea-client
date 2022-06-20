package com.rarible.opensea.client.model.v2

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.daonomic.rpc.domain.Binary
import io.daonomic.rpc.domain.Word
import java.math.BigDecimal
import java.time.Instant

data class SeaPortOrder(
    @JsonProperty("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][z]", timezone = "UTC")
    val createdAt: Instant,

    @JsonProperty("closing_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSS][z]", timezone = "UTC")
    val closingDate: Instant?,

    @JsonProperty("listing_time")
    val listingTime: Long,

    @JsonProperty("expiration_time")
    val expirationTime: Long,

    @JsonProperty("order_hash")
    val orderHash: Word?,

    @JsonProperty("protocol_data")
    val protocolData: ProtocolData,

    @JsonProperty("protocol_address")
    val protocolAddress: String?,

    val maker: Account,

    val taker: Account?,

    @JsonProperty("current_price")
    val currentPrice: BigDecimal,

    @JsonProperty("maker_fees")
    val makerFees: List<Fee>,

    @JsonProperty("taker_fees")
    val takerFees: List<Fee>,

    val side: OrderSide,

    @JsonProperty("order_type")
    val orderType: OrderType,

    val canceled: Boolean,

    val finalized: Boolean,

    @JsonProperty("marked_invalid")
    val markedInvalid: Boolean,

    @JsonProperty("client_signature")
    val clientSignature: Binary?
)


