package com.rarible.opensea.subscriber.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.Instant

data class OpenseaEvent(
    val eventId: String,
    val event: OpenseaEventType,
    val eventTimeMarks: EventTimeMarks,

    @JsonTypeInfo(property = "event", use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY)
    @JsonSubTypes(
        JsonSubTypes.Type(name = "item_cancelled", value = OpenseaOrderInfoPayload::class),
        JsonSubTypes.Type(name = "order_invalidated", value = OpenseaOrderInfoPayload::class),
        JsonSubTypes.Type(name = "order_revalidated", value = OpenseaOrderInfoPayload::class),
    )
    val payload: OpenseaEventPayload
)

sealed class OpenseaEventPayload

data class OpenseaOrderInfoPayload(
    val orderHash: String,
    val eventTimestamp: Instant,
): OpenseaEventPayload()
