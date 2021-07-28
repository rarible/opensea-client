package com.rarible.opensea.client.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.daonomic.rpc.domain.Word
import scalether.domain.Address
import java.math.BigDecimal
import java.math.BigInteger

data class OpenSeaOrder(
    val id: BigInteger,

    @JsonProperty("order_hash")
    val orderHash: Word,

    val asset: Asset,

    val maker: Maker,

    val taker: Taker,

    @JsonProperty("base_price")
    val basePrice: BigDecimal,

    @JsonProperty("current_price")
    val currentPrice: BigDecimal,

    @JsonProperty("current_bounty")
    val currentBounty: BigDecimal,

    @JsonProperty("bounty_multiple")
    val bountyMultiple: BigDecimal,

    @JsonProperty("fee_recipient")
    val feeRecipient: FeeRecipient,

    val side: OrderSide,

    @JsonProperty("payment_token")
    val paymentToken: Address,

    @JsonProperty("payment_token_contract")
    val paymentTokenContract: PaymentTokenContract,

    val salt: BigInteger,

    @JsonProperty("approved_on_chain")
    val approvedOnChain: Boolean,

    val cancelled: Boolean,

    val finalized: Boolean,

    @JsonProperty("marked_invalid")
    val markedInvalid: Boolean,

    @JsonProperty("prefixed_hash")
    val prefixedHash: Word
)
