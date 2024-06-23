package com.example.zebracoffee.presentation.home.stories

import androidx.lifecycle.ViewModel
import com.example.zebracoffee.data.modelDto.Stories
import com.example.zebracoffee.utils.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StoriesScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _storiesList = MutableStateFlow<List<Stories>>(emptyList())
    val storiesList = _storiesList.asStateFlow()

    private val _isLoadingStories = MutableStateFlow(false)
    val isLoadingStories = _isLoadingStories.asStateFlow()

    fun getValidImageAddress(url: String): String {
        val baseUrl = Constant.BASE_URL.dropLast(1)
        return baseUrl + url
    }
}