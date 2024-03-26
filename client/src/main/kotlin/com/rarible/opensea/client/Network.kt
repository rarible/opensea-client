package com.rarible.opensea.client

/**
 * https://docs.opensea.io/reference/supported-chains
 */
enum class Network(val value: String) {
    SEPOLIA("sepolia"),
    GOERLI("goerli"),
    MUMBAI("mumbai"),
    ETHEREUM("ethereum"),
    POLYGON("matic"),
    ARBITRUM_SEPOLIA("arbitrum_sepolia"),
    ARBITRUM("arbitrum"),
    BASE("base"),
    BASE_SEPOLIA("base_sepolia"),
}