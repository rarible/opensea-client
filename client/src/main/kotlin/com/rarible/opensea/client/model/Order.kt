package com.rarible.opensea.client.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.daonomic.rpc.domain.Word
import scalether.domain.Address
import java.math.BigDecimal
import java.math.BigInteger

data class Order(
    val id: BigInteger,

    @JsonProperty("order_hash")
    val orderHash: Word,

    val asset: Asset,

    val maker: Maker,

    val taker: Taker,

    val basePrice: BigDecimal,

    val currentPrice: BigDecimal,

    val currentBounty: BigDecimal,

    val bountyMultiple: BigDecimal,

    val feeRecipient: FeeRecipient,

    val side: OrderSide,

    val paymentToken: Address,

    val paymentTokenContract: PaymentTokenContract,

    val salt: BigInteger,

    val approvedOnChain: Boolean,

    val cancelled: Boolean,

    val finalized: Boolean,

    val markedInvalid: Boolean,

    val prefixedHash: Word
)
