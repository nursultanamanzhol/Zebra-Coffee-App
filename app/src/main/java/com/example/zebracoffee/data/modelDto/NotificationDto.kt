package com.example.zebracoffee.data.modelDto


import com.example.zebracoffee.domain.entity.Notification
import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("body") override val body: String,
    @SerializedName("datetime")override val datetime: String,
    @SerializedName("id")override val id: Int,
    @SerializedName("notification_url")override val notification_url: String?,
    @SerializedName("title")override val title: String,
    @SerializedName("type")override val type: Type?,
): Notification