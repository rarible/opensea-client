package com.rarible.opensea.client.model

import scalether.domain.Address

data class Maker(
    val address: Address,
    val user: User?
)
