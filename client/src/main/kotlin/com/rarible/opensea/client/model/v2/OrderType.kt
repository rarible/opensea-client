package com.rarible.opensea.client.model.v2

enum class OrderType(val value: Int) {
    // No partial fills, anyone can execute
    FULL_OPEN(0),
    // Partial fills supported, anyone can execute
    PARTIAL_OPEN(1),
    // No partial fills, only offerer or zone can execute
    FULL_RESTRICTED(2),
    // Partial fills supported, only offerer or zone can execute
    PARTIAL_RESTRICTED(3),
    // Special type of order when offerer is contract
    CONTRACT(4)
    ;
}