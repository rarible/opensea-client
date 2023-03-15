package com.rarible.opensea.client.model.v2

import io.daonomic.rpc.domain.Binary

data class ProtocolData(
    val parameters: OrderParameters,
    val signature: Binary?
)