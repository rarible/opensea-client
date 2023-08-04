package com.rarible.opensea.subscriber.model

data class EventTimeMarks (
    val source : String,
    val marks : List<EventTimeMark> = listOf()
)
