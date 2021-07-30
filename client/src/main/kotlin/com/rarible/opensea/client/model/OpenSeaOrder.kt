package com.rarible.opensea.client.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.daonomic.rpc.domain.Binary
import io.daonomic.rpc.domain.Word
import scalether.domain.Address
import java.math.BigDecimal
import java.math.BigInteger

data class OpenSeaOrder(
    val id: BigInteger,

    val exchange: Address,

    val maker: Maker,

    val taker: Taker,

    @JsonProperty("maker_relayer_fee")
    val makerRelayerFee: BigInteger,

    @JsonProperty("taker_relayer_fee")
    val takerRelayerFee: BigInteger,

    @JsonProperty("maker_protocol_fee")
    val makerProtocolFee: BigInteger,

    @JsonProperty("taker_protocol_fee")
    val takerProtocolFee: BigInteger,

    @JsonProperty("fee_recipient")
    val feeRecipient: FeeRecipient,

    @JsonProperty("fee_method")
    val feeMethod: FeeMethod,

    val side: OrderSide,

    @JsonProperty("sale_kind")
    val saleKind: SaleKind,

    val target: Address,

    @JsonProperty("how_to_call")
    val howToCall: HowToCall,

    @JsonProperty("calldata")
    val callData: Binary,

    @JsonProperty("replacement_pattern")
    val replacementPattern: Binary,

    @JsonProperty("static_target")
    val staticTarget: Address,

    @JsonProperty("static_extradata")
    val staticExtraData: Binary,

    @JsonProperty("payment_token")
    val paymentToken: Address,

    @JsonProperty("base_price")
    val basePrice: BigInteger,

    val extra: BigInteger,

    @JsonProperty("listing_time")
    val listingTime: Long,

    @JsonProperty("expiration_time")
    val expirationTime: Long,

    val salt: BigInteger,

    @JsonProperty("order_hash")
    val orderHash: Word,

    val asset: Asset,

    @JsonProperty("current_price")
    val currentPrice: BigDecimal,

    @JsonProperty("current_bounty")
    val currentBounty: BigDecimal,

    @JsonProperty("bounty_multiple")
    val bountyMultiple: BigDecimal,

    @JsonProperty("payment_token_contract")
    val paymentTokenContract: PaymentTokenContract,

    @JsonProperty("approved_on_chain")
    val approvedOnChain: Boolean,

    val cancelled: Boolean,

    val finalized: Boolean,

    @JsonProperty("marked_invalid")
    val markedInvalid: Boolean,

    @JsonProperty("prefixed_hash")
    val prefixedHash: Word,

    val quantity: BigInteger,

    val v: BigInteger,

    val r: Binary,

    val s: Binary
)
