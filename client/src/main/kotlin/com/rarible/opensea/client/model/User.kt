package com.rarible.opensea.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    @field:JsonProperty("username")
    val name: String?
)
