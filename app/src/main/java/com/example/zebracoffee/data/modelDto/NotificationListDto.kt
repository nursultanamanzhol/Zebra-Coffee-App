package com.example.zebracoffee.data.modelDto

import com.example.zebracoffee.domain.entity.NotificationList


data class NotificationListDto(
    override val count: Int,
    override val next: Any?,
    override val page_number: Int,
    override val per_page: Int,
    override val previous: Any?,
    override val results: List<NotificationDto>,
    override val total_pages: Int
): NotificationList