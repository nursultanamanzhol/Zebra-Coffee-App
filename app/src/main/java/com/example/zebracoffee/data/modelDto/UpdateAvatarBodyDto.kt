package com.example.zebracoffee.data.modelDto

import androidx.annotation.Keep
import com.example.zebracoffee.domain.entity.UpdateAvatarBody
import com.google.gson.annotations.SerializedName

@Keep
data class UpdateAvatarBodyDto(
    @SerializedName("avatar_choice") override val avatarChoice: Int
) : UpdateAvatarBody