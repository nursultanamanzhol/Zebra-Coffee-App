package com.example.zebracoffee.domain.useCases.loyaltyCardUseCase

import com.example.zebracoffee.data.modelDto.NotificationListDto
import com.example.zebracoffee.domain.entity.Notification


interface GetNotificationUseCase {
    suspend fun getNotificationMessages(bearerToken: String, page: Int, limit: Int ): NotificationListDto
}