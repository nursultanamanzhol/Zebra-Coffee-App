package com.example.zebracoffee.presentation.coffeeShops.coffeeShopDetails

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.menuDto.MenuDto
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.GetCoffeeShopsUseCase
import com.example.zebracoffee.domain.useCases.menuUseCases.GetMenuByShopIdUseCase
import com.example.zebracoffee.domain.useCases.menuUseCases.GetSearchHistoryUseCase
import com.example.zebracoffee.domain.useCases.menuUseCases.GetSearchProductUseCase
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeShopDetailsViewModel @Inject constructor(
    private val coffeeShopsUseCase: GetCoffeeShopsUseCase,
    private val getMenuByShopIdUseCase: GetMenuByShopIdUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getSearchProductUseCase: GetSearchProductUseCase,
    val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _openBottomSearch = MutableStateFlow(false)
    val openBottomSearch = _openBottomSearch.asStateFlow()


    fun setClickSearchTrue() {
        viewModelScope.launch {
            _openBottomSearch.emit(true)
            Log.d("isVisible", _openBottomSearch.value.toString())
        }
    }

    fun setClickSearchFalse() {
        viewModelScope.launch {
            _openBottomSearch.emit(false)
            Log.d("isVisible", _openBottomSearch.value.toString())
        }
    }


    private val _coffeeShop = MutableStateFlow<Resource<CoffeeDetails>>(Resource.Unspecified)
    val coffeeShop = _coffeeShop.asStateFlow()

    val bearerToken = sharedPreferences.getString("access_token", null)

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

    private val _isLoadingMenu = MutableStateFlow(true)
    val isLoadingMenu = _isLoadingMenu.asStateFlow()

    fun getCoffeeShop(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _coffeeShop.value = Resource.Loading
            try {
                val shops = bearerToken?.let { coffeeShopsUseCase.getCoffeeShopDetails(it, id) }
                if (shops != null) {
                    if (shops.isSuccessful) {
                        _coffeeShop.value = Resource.Success(shops.body())
                    }
                }
            } catch (e: Exception) {
                _coffeeShop.value = Resource.Failure(e.message.toString())
                Log.e("CoffeeShopsViewModel", "Failed to fetch coffee shop details: ${e.message}")
            }
        }
    }

    private val _menuStore = MutableStateFlow<MenuDto?>(null)
    val menuStore = _menuStore.asStateFlow()

    fun getMenuStore(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingMenu.value = true
            kotlin.runCatching {
                getMenuByShopIdUseCase.getMenuByShopId(id, bearerToken!!)
            }.fold(
                onSuccess = { result ->
                    _menuStore.value = result
                    Log.d("API1", "Data received: ${result.toString()}")
                },
                onFailure = { e ->
                    Log.d("HomeViewModel", "Error encountered: ${e.message}")
                }
            )
            _isLoadingMenu.value = false
        }
    }

    private val _searchHistory = MutableStateFlow<MenuDto?>(null)
    val searchHistory = _searchHistory.asStateFlow()

    fun getSearchHistory(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingMenu.value = true
            kotlin.runCatching {
                getSearchHistoryUseCase.getSearchHistory(id, bearerToken!!)
            }.fold(
                onSuccess = { result ->
                    _searchHistory.value = result
                    Log.d("API1", "Data received: ${result.toString()}")
                },
                onFailure = { e ->
                    Log.d("HomeViewModel", "Error encountered: ${e.message}")
                }
            )
            _isLoadingMenu.value = false
        }
    }

    private val _searchProduct = MutableStateFlow<MenuDto?>(null)
    val searchProduct = _searchProduct.asStateFlow()

    fun getProductInSearch(search: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingMenu.value = true
            kotlin.runCatching {
                getSearchProductUseCase.getSearchProduct(search, id, bearerToken!!)
            }.fold(
                onSuccess = { result ->
                    _searchProduct.value = result
                    Log.d("API1", "Data received: ${result.toString()}")
                },
                onFailure = { e ->
                    Log.d("HomeViewModel", "Error encountered: ${e.message}")
                }
            )
            _isLoadingMenu.value = false
        }
    }
}
