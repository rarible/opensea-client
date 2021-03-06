package com.rarible.opensea.client.model.v2

enum class ItemType(val value: Int) {
    NATIVE(0),
    ERC20(1),
    ERC721(2),
    ERC1155(3),
    ERC721_WITH_CRITERIA(4),
    ERC1155_WITH_CRITERIA(5)
    ;
}