package com.rarible.opensea.subscriber

class OpenseaTopicProvider {

    companion object {
        fun getEventTopic(environment: String, chain: String): String =
            "protocol.$environment.$chain.opensea.event"
        const val VERSION = "1"
    }
}