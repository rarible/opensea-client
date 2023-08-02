package com.rarible.opensea.subscriber.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.Instant

data class OpenseaEvent(
    val event: OpenseaEventType,

    @JsonTypeInfo(property = "event", use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes(
        JsonSubTypes.Type(name = "item_cancelled", value = OpenseaItemCancelled::class),
    )
    val payload: OpenseaEventPayload
)

sealed class OpenseaEventPayload

data class OpenseaItemCancelled(
    val orderHash: String,
    val eventTimestamp: Instant,
    val maker: String?,
): OpenseaEventPayload()
