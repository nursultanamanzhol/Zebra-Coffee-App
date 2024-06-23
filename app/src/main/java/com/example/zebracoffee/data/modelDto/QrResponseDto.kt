package com.example.zebracoffee.data.modelDto

import com.example.zebracoffee.domain.entity.QrResponse
import com.google.gson.annotations.SerializedName

data class QrResponseDto(
    override val active: Boolean,
    override val code: Int,
    @SerializedName("creation_date") override val creationDate: String,
    @SerializedName("expiration_date") override val expirationDate: String,
    override val id: Int,
    override val qr: String,
    override val user: Int,
) : QrResponse