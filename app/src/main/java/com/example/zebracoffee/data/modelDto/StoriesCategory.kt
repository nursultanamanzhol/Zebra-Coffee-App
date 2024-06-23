package com.example.zebracoffee.data.modelDto

data class StoriesCategory(
    val id: Int,
    val image: String? ,
    val name: String?,
    val stories: List<Stories>,
    val title: String?,
    val type: String?
)