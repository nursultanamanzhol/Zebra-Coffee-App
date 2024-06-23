package com.example.zebracoffee.data.modelDto

import com.example.zebracoffee.domain.entity.AvatarImageList

data class AvatarImageListDto(
    override val count: Int,
    override val next: Any,
    override val page_number: Int,
    override val per_page: Int,
    override val previous: Any,
    override val results: List<AvatarImageDto>,
    override val total_pages: Int,
): AvatarImageList