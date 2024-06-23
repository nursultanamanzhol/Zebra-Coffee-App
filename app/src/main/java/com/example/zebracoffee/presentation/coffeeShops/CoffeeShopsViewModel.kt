package com.example.zebracoffee.presentation.coffeeShops

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.R
import com.example.zebracoffee.data.modelDto.City
import com.example.zebracoffee.data.modelDto.CoffeeDetails
import com.example.zebracoffee.data.modelDto.Country
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.domain.useCases.coffeeShopsUseCase.GetCoffeeShopsUseCase
import com.example.zebracoffee.presentation.coffeeShops.map.LocationTracker
import com.example.zebracoffee.presentation.coffeeShops.map.clustering.ClusteringData
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.example.zebracoffee.utils.Constant
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject
import kotlin.math.*

@HiltViewModel
class CoffeeShopsViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val coffeeShopsUseCase: GetCoffeeShopsUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _activeItem = MutableStateFlow<Int?>(null)
    val activeItem: StateFlow<Int?> = _activeItem.asStateFlow()
    fun activateItem(itemId: Int) {
        _activeItem.value = itemId
    }

    fun deactivateItem() {
        _activeItem.value = null
    }
    var isShopInitialized = false
    var isShopInitializedCountry = false

    fun isShopInitialized(cityId: Int, latitude: Double, longitude: Double) {
        if (!isShopInitialized) {
            viewModelScope.launch(Dispatchers.IO) {
//                getCountryList()
                getCoffeeShopCity(cityId, latitude, longitude)
                isShopInitialized = true
            }
        }
    }
    fun getShopInitializedCountry() {
        if (!isShopInitializedCountry) {
            viewModelScope.launch(Dispatchers.IO) {
                getCountryList()
                isShopInitializedCountry = true
            }
        }
    }
    var currentLocation by mutableStateOf<Location?>(null)

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    fun refresh(cityId: Int, latitude: Double, longitude: Double) {
        _isRefreshing.value = true // Установите флаг обновления в true

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000) // Имитация задержки загрузки данных
            try {
                coroutineScope {
                    launch { getCountryList() }
                    launch { getCoffeeShopCity(cityId, latitude, longitude) } // Передача параметров
                    launch { getCityId() }
                }
            } finally {
                _isRefreshing.value = false // Установите флаг обновления обратно в false
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private val _countryList = MutableStateFlow<List<Country>>(emptyList())
    val countryList = _countryList.asStateFlow()

    private val _isLoadingShops = MutableStateFlow(false)
    val isLoadingShops = _isLoadingShops.asStateFlow()

    private var isLoadingCountry = mutableStateOf(false)

    private val _coffeeShopCityList = MutableStateFlow<List<CoffeeDetails>>(emptyList())
    val coffeeShopCityList = _coffeeShopCityList.asStateFlow()

    private val _uiState = MutableStateFlow(false)
    val uiState = _uiState.asStateFlow()

    private val _clickFacility = MutableStateFlow(false)
    val clickFacility = _clickFacility.asStateFlow()

    fun setClickFacilityTrue() {
        viewModelScope.launch {
            _clickFacility.emit(true)
            Log.d("isVisible", _clickFacility.value.toString())
        }
    }

    fun setClickFacilityFalse() {
        viewModelScope.launch {
            _clickFacility.emit(false)
            Log.d("isVisible", _clickFacility.value.toString())
        }
    }


    private val _userImage = MutableStateFlow("")
    val userImage: StateFlow<String> = _userImage

    val bearerToken = sharedPreferences.getString("access_token", null)
    private val userImageFromSharedPreferences = sharedPreferences.getString("avatar_image", null)

    private val _selectedShop = MutableStateFlow<ClusteringData?>(null)
    val selectedShop: StateFlow<ClusteringData?> = _selectedShop
    fun setSelectedShop(item: ClusteringData) {
        _selectedShop.value = item
    }


    init {
        _userImage.value = userImageFromSharedPreferences ?: ""
    }

    fun changedUiState() {
        viewModelScope.launch {
            _uiState.emit(!_uiState.value)
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            currentLocation = locationTracker.getCurrentLocation()
        }
    }

    fun getCountryList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoadingCountry.value = true
                val countries = bearerToken?.let { coffeeShopsUseCase.getCountryList(it) }
                _countryList.value = countries!!
            } catch (e: Exception) {
                Log.e("CoffeeShopsViewModel", "Failed to fetch country list: ${e.message}")
            }
            isLoadingCountry.value = false
        }
    }

    fun getCoffeeShopCity(cityId: Int, latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoadingShops.value = true
                val shops = bearerToken?.let {
                    coffeeShopsUseCase.getCoffeeShopByCity(
                        it,
                        cityId,
                        latitude,
                        longitude
                    )
                }
                _coffeeShopCityList.value = shops!!
            } catch (e: Exception) {
                _isLoadingShops.value = false
                Log.e("CoffeeShopsViewModel", "Failed to fetch coffee shop details: ${e.message}")
            } finally {
                _isLoadingShops.value = false
            }
        }
    }

    fun resetState() {
        _isLoadingShops.value = false
    }

    fun saveCitySharedPref(city: City) {
        val json = Gson().toJson(city)
        sharedPreferences.edit().putString("saved_city_coffeeShop", json).apply()
    }

    fun getCitySharedPref(): City {
        val json = sharedPreferences.getString("saved_city_coffeeShop", "{}")
        if (json == "{}") return City(
            id = -1,
            country = -1,
            name = "notSelect",
            latitude = 0.0,
            longitude = 0.0
        )
        return Gson().fromJson(json, City::class.java)
    }

    fun getCityId(): Int {
        val city = getCitySharedPref()
        return if (city.id == -1) -1
        else city.id
    }


    fun chooseTopBarTitle(context: Context): String {
        return if (getCitySharedPref().name == "notSelect") context.getString(R.string.coffee_city_name_select)
        else getCitySharedPref().name.uppercase()
    }

    fun getValidImageAddress(url: String?): String {
        return if (url != null) {
            val baseUrl = Constant.BASE_URL.dropLast(1)
            baseUrl + url
        } else {
            "https://cdn-icons-png.flaticon.com/512/4054/4054617.png"
        }.toString()
    }

    @Composable
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWorksTime(openFrom: String, openUntil: String): Pair<String, Painter> {
        var openUntil1 = ""
        if (openUntil == "00:00:00") openUntil1 = "23:59:59"
        else openUntil1 = openUntil
        val currentTime = LocalTime.now()
        var openFromTime = LocalTime.parse(openFrom)
        var openUntilTime = LocalTime.parse(openUntil1)

        if (openUntilTime.isBefore(openFromTime)) {
            openUntilTime = openUntilTime.plusHours(24)
        }

        return when {
            currentTime.isAfter(openUntilTime) -> Pair(
                "Закрыто до завтра, ${openFrom.dropLast(3)}",
                painterResource(id = R.drawable.ellipse_red)
            )

            currentTime.plusMinutes(60).isAfter(openUntilTime) -> {
                val remainingTime =
                    currentTime.until(openUntilTime, java.time.temporal.ChronoUnit.MINUTES)
                Pair(
                    "Закроется через ${remainingTime}мин",
                    painterResource(id = R.drawable.ellipse_orange)
                )
            }

            else -> {
                val remainingTime =
                    currentTime.until(openUntilTime, java.time.temporal.ChronoUnit.MINUTES)
                Pair(
                    "Открыто до ${openUntil.dropLast(3)}",
                    painterResource(id = R.drawable.ellipse_blue)
                )
            }
        }
    }
}
