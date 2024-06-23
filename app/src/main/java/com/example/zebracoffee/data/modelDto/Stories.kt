package com.example.zebracoffee.data.modelDto

data class Stories(
    val body: StoriesBody,
    val category: Int,
    val duration: Int,
    val footer: StoriesFooter,
    val header: StoriesHeader,
    val id: Int,
    val link: String,
    val type: String
)