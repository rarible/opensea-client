package com.rarible.opensea.client.model.v1

import scalether.domain.Address

data class Maker(
    val address: Address,
    /**
     * TODO: need fix serialization
     * 1)
     * "user": {
     *   "username": "NullAddress"
     *  }
     * 2)
     * "user": 150875
     */
    //val user: User? //
)
