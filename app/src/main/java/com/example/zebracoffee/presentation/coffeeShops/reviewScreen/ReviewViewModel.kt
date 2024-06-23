package com.example.zebracoffee.presentation.coffeeShops.reviewScreen

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.ReviewListItem
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.CreateNewReviewUseCase
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.UpdateReviewUseCase
import com.example.zebracoffee.domain.useCases.reviewUseCase.GetReviewByShopIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val getReviewByShopIdUseCase: GetReviewByShopIdUseCase,
    private val createNewReviewUseCase: CreateNewReviewUseCase,
    private val updateReviewUseCase: UpdateReviewUseCase,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    private val _reviewMessage = MutableStateFlow<List<ReviewListItem>>(emptyList())
    val reviewMessage = _reviewMessage.asStateFlow()

    private val _isLoadingReview = MutableStateFlow(true)
    val isLoadingReview = _isLoadingReview.asStateFlow()

    private val _rating = MutableStateFlow(0.0)
    val rating = _rating.asStateFlow()

    private val _newReview = MutableStateFlow<Resource<ReviewListItem>>(Resource.Unspecified)
    val newReview = _newReview.asStateFlow()

    private val _updateReview = MutableStateFlow<Resource<ReviewListItem>>(Resource.Unspecified)
    val updateReview = _updateReview.asStateFlow()

    private val bearerToken = sharedPreferences.getString("access_token", null)

    init {
        rating
    }

    fun getReviewMessages(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _isLoadingReview.value = true
                val reviewList = getReviewByShopIdUseCase.getReviewByShopId(id, bearerToken!!)
                reviewList
            }.fold(
                onSuccess = { result ->
                    _reviewMessage.value = result.results
                    Log.d("API1", result.results.size.toString())
                },
                onFailure = { e -> Log.d("HomeViewModel", e.message.toString()) }
            )
            _isLoadingReview.value = false
        }
    }

    fun createNewReview(grade: Int, text: String, shopId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _newReview.value = Resource.Loading
            try {
                val result = createNewReviewUseCase.createNewRequest(
                    bearerToken!!,
                    grade,
                    text,
                    shopId
                )
                _newReview.value = Resource.Success(result.body())
                getReviewMessages(shopId)
            } catch (e: Exception) {
                _newReview.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun updateReview(grade: Int, text: String, shopId: Int,reviewId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _updateReview.value = Resource.Loading
            try {
                val result = updateReviewUseCase.updateReview(
                    bearerToken!!,
                    grade,
                    text,
                    shopId,
                    reviewId
                )
                _updateReview.value = Resource.Success(result.body())
                getReviewMessages(shopId)
            } catch (e: Exception) {
                _updateReview.value = Resource.Failure(e.message.toString())
            }
        }
    }

    private fun rating(listItem: List<ReviewListItem>) {
        val shopRating = listItem.firstOrNull()
        shopRating?.let { setRating(it.shop_rating.toDouble()) }
    }

    fun setRating(rating: Double) {
        _rating.update { rating }
    }
}