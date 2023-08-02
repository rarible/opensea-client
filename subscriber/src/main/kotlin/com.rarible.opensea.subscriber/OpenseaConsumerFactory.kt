package com.rarible.opensea.subscriber

import com.rarible.core.kafka.RaribleKafkaConsumerSettings
import com.rarible.opensea.subscriber.model.OpenseaEvent
import org.apache.kafka.clients.consumer.OffsetResetStrategy

class OpenseaConsumerFactory(
    private val brokerReplicaSet: String,
    private val host: String,
    private val environment: String
) {

    fun createEventConsumerSettings(
        group: String,
        concurrency: Int,
        batchSize: Int,
        blockchain: String
    ) : RaribleKafkaConsumerSettings<OpenseaEvent> {
        return RaribleKafkaConsumerSettings(
            hosts = brokerReplicaSet,
            topic = OpenseaTopicProvider.getEventTopic(environment, blockchain),
            group = group,
            concurrency = concurrency,
            batchSize = batchSize,
            offsetResetStrategy = OffsetResetStrategy.EARLIEST,
            valueClass = OpenseaEvent::class.java
        )
    }
}