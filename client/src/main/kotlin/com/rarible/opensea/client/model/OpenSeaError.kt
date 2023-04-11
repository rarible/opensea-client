package com.rarible.opensea.client.model

data class OpenSeaError(
    val httpCode: Int,
    val code: OpenSeaErrorCode,
    val message: String
) {
    fun isGeneratingFulfillmentDataError(): Boolean {
        return code == OpenSeaErrorCode.BAD_REQUEST && message.contains(ERROR_MESSAGE)
    }
}

private const val ERROR_MESSAGE = "Error when generating fulfillment data"