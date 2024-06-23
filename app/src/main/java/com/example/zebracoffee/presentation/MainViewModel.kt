package com.example.zebracoffee.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.navigation.MainDestinations
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    /* private val _splashCondition = mutableStateOf(true)
     val splashCondition: State<Boolean> = _splashCondition*/
    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(MainDestinations.OnBoardingScreen_route)
        private set

    /*init {
        appEntryUseCases.readAppEntry().onEach { startFromTypeNumber ->
            startDestination = if (startFromTypeNumber) {
                MainDestinations.TypeNumberScreen_route
            } else {
                MainDestinations.OnBoardingScreen_route
            }
        }.launchIn(viewModelScope)
    }*/

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

}