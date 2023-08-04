package com.rarible.opensea.subscriber.model

import java.time.Instant

data class EventTimeMark (
    val name : String,
    val date : Instant
)
