package com.example.zebracoffee.domain.useCases.loyaltyCardUseCase

import com.example.zebracoffee.data.modelDto.NotificationListDto
import com.example.zebracoffee.domain.repository.RegistrationRepository
import com.example.zebracoffee.domain.entity.Notification
import javax.inject.Inject

class GetNotificationUseCaseImpl @Inject constructor(
    private val repository: RegistrationRepository
) : GetNotificationUseCase {

    override suspend fun getNotificationMessages(bearerToken: String, page: Int, limit: Int): NotificationListDto {
        return repository.getNotificationMessages(bearerToken, page, limit)
    }
}