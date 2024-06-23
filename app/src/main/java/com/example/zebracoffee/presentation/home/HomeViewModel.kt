package com.example.zebracoffee.presentation.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.data.modelDto.CheckVersionDto
import com.example.zebracoffee.data.modelDto.FCMRegistrationTokenDto
import com.example.zebracoffee.data.modelDto.LoyaltyCardResponseDto
import com.example.zebracoffee.data.modelDto.News
import com.example.zebracoffee.data.modelDto.Resource
import com.example.zebracoffee.data.modelDto.StoriesCategory
import com.example.zebracoffee.data.modelDto.UserDto
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.domain.useCases.homeUseCase.CheckAppVersionUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.GetCategoryStoriesUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.GetNewsByIdUseCase
import com.example.zebracoffee.domain.useCases.homeUseCase.GetNewsListUseCase
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.FCMRegistrationTokenUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetLoyaltyCardInfoUseCase
import com.example.zebracoffee.domain.useCases.registrationUseCase.GetUserDataUseCase
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storiesUseCase: GetCategoryStoriesUseCase,
    private val loyaltyCardInfoUseCase: GetLoyaltyCardInfoUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val newsListUseCase: GetNewsListUseCase,
    private val checkAppVersionUseCase: CheckAppVersionUseCase,
    private val getNewsByIdUseCase: GetNewsByIdUseCase,
    private val sharedPreferences: SharedPreferences,
    private val fcmRegistrationTokenUseCase: FCMRegistrationTokenUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    fun refresh() {
        _isRefreshing.value = true // Установите флаг обновления в true

        viewModelScope.launch(Dispatchers.IO) {
            // Имитация задержки загрузки данных
            delay(2000)
            try {
                // Параллельное выполнение всех задач обновления данных
                coroutineScope {
                    launch { getUserData() }
                    launch { getLoyaltyCardInfo() }
                    launch { getNewsList() }
                    launch { getStoriesList() }
                    // Добавьте сюда любые другие методы обновления данных, которые вы хотите выполнить
                }
            } finally {
                _isRefreshing.value =
                    false // Установите флаг обновления обратно в false после загрузки данных
            }
        }
    }

    private val _userDataCard = MutableStateFlow<Resource<UserDto>>(Resource.Loading)
    val userDataCard = _userDataCard.asStateFlow()

    private val _loyaltyCard =
        MutableStateFlow<Resource<LoyaltyCardResponseDto>>(Resource.Loading)
    val loyaltyCard = _loyaltyCard.asStateFlow()

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList = _newsList.asStateFlow()

    private val _isLoadingNews = MutableStateFlow(false)
    val isLoadingNews = _isLoadingNews.asStateFlow()

    private val _isLoadingStories = MutableStateFlow(false)
    val isLoadingStories = _isLoadingStories.asStateFlow()

    private val _storiesList = MutableStateFlow<List<StoriesCategory>>(emptyList())
    val storiesList = _storiesList.asStateFlow()

    private val _appVersion = MutableStateFlow<Resource<CheckVersionDto>>(Resource.Unspecified)
    val appVersion = _appVersion.asStateFlow()

    private val _newsById = MutableStateFlow<Resource<News>>(Resource.Unspecified)
    val newsById = _newsById.asStateFlow()

    private val bearerToken = sharedPreferences.getString("access_token", null)

    var isInitialized = false
    var visually = false

    fun initializeData() {
        if (!isInitialized) {
            viewModelScope.launch(Dispatchers.IO) {
                getUserData()
                getLoyaltyCardInfo()
                getNewsList()
                getStoriesList()
                visually = true
                isInitialized = true
            }
        }
    }

    init {
        initializeData()
        Log.d("TestUpdating", isInitialized.toString())
    }

    fun getStoriesList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoadingStories.value = true
                val storiesList = bearerToken?.let { storiesUseCase.getStoriesCategory(it) }
                if (storiesList != null) {
                    _storiesList.value = storiesList
                }
            } catch (e: Exception) {
                _isLoadingStories.value = false
                Log.d("HomeViewModel", e.message.toString())
            }
            _isLoadingStories.value = false
        }
    }

    private fun getNewsList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoadingNews.value = true
                val newsList = bearerToken?.let { newsListUseCase.getNewsList(it) }
                if (newsList != null) {
                    _newsList.value = newsList
                }
            } catch (e: Exception) {
                _isLoadingNews.value = false
                Log.d("HomeViewModel", e.message.toString())
            }
            _isLoadingNews.value = false
        }
    }


    fun getUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            _userDataCard.value = Resource.Loading
            try {
                val result =
                    getUserDataUseCase.getUserData(bearerToken!!)
                if (result.isSuccessful) {
                    _userDataCard.value = Resource.Success(result.body())
                } else {
                    _userDataCard.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _userDataCard.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun getLoyaltyCardInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _loyaltyCard.value = Resource.Loading
            try {
                val result = loyaltyCardInfoUseCase.getLoyaltyCardInfo(bearerToken!!)
                if (result.isSuccessful) {
                    _loyaltyCard.value = Resource.Success(result.body())
                } else {
                    _loyaltyCard.value = Resource.Failure(result.message())
                    if (result.code() == 401) {
                        // Перепишите refreshToken для использования корутин и обработки возвращаемого значения
                    }
                }
            } catch (e: Exception) {
                _loyaltyCard.value = Resource.Failure(e.message.toString())
            }
        }
    }


    fun checkAppVersion(platform: String, currentVersion: String) {
        viewModelScope.launch {
            _appVersion.value = Resource.Loading
            try {
                val result =
                    checkAppVersionUseCase.checkAppVersion(platform, currentVersion, bearerToken!!)
                if (result.isSuccessful) {
                    _appVersion.value = Resource.Success(result.body())
                } else {
                    _appVersion.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _appVersion.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun getNewsById(id: Int) {
        viewModelScope.launch {
            _newsById.value = Resource.Loading
            try {
                val result =
                    getNewsByIdUseCase.getNewsById(bearerToken!!, id)
                if (result.isSuccessful) {
                    _newsById.value = Resource.Success(result.body())
                } else {
                    _newsById.value = Resource.Failure(result.message())
                }
            } catch (e: Exception) {
                _newsById.value = Resource.Failure(e.message.toString())
            }
        }
    }

    fun setFcmToken() {
        Log.d("MyTag", "checkNotificationsPermission : ${checkNotificationsPermission()}")
        viewModelScope.launch {
            try {
                val bearerToken = sharedPreferences.getString("access_token", null)
                val tokenFcm = fcmToken()
                saveFcmTokenToPef(context, fcmToken())
                val res = fcmRegistrationTokenUseCase.setFCMRegistrationToken(
                    FCMRegistrationTokenDto(
                        device_token = tokenFcm,
                        is_client_push_allowed = checkNotificationsPermission(),
                        platform = "android"
                    ),
                    bearerToken!!
                )
                if (res.isSuccessful) {
                    Log.d("MyTag", "res.isSuccessful")
                } else {
                    Log.d("MyTag", "res. is NOT Successful")
                }

            } catch (e: Exception) {
                Log.e("MyTag", "Error fetching FCM token")
            }
        }
    }

    suspend fun fcmToken(): String {
        return suspendCoroutine { continuation ->
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    continuation.resume(token)
                } else {
                    continuation.resumeWithException(
                        task.exception
                            ?: IllegalStateException("Fetching FCM registration token failed")
                    )
                }
            }
        }
    }

    private fun saveFcmTokenToPef(context: Context, value: String) {
        val sharedPreferences = context.getSharedPreferences("FCMToken", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("fcmToken", value)
            apply()
        }
    }

    fun checkNotificationsPermission(): Boolean {
        return (context.applicationContext as HiltApplication).areNotificationsEnabled()
    }
}



