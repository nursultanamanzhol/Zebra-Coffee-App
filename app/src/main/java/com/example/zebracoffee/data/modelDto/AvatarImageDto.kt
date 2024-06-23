package com.example.zebracoffee.data.modelDto

import com.example.zebracoffee.domain.entity.AvatarImage

data class AvatarImageDto(
    override val avatar: String,
    override val id: Int,
    override val name: String
) : AvatarImage