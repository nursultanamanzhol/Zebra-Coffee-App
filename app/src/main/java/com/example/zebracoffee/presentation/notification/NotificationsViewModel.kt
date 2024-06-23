package com.example.zebracoffee.presentation.notification


import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.zebracoffee.R
import com.example.zebracoffee.data.NotificationPagingSource
import com.example.zebracoffee.di.HiltApplication
import com.example.zebracoffee.domain.entity.Notification
import com.example.zebracoffee.domain.repository.RegistrationRepository
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.GetNotificationUseCase
import com.example.zebracoffee.presentation.profileScreen.interfaceApp.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val viewModel: GetNotificationUseCase,
    private val sharedPreferences: SharedPreferences,
    private val repository: RegistrationRepository,
) : ViewModel() {

    val bearerToken = sharedPreferences.getString("access_token", null)
    val hasMorePages = MutableStateFlow<Boolean>(true)
    val pager: Flow<PagingData<Notification>> = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            NotificationPagingSource(bearerToken = bearerToken!!, viewModel, 10, hasMorePages)
        }
    ).flow.cachedIn(viewModelScope)

    val theme: StateFlow<AppTheme> = HiltApplication.prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.AUTO,
    )

//    init {
//        getNotificationMessages()
//    }
    //val notificationState = _notificationMessage.asStateFlow()

//    fun getNotificationMessages() {
//        viewModelScope.launch {
//            try {
//                val bearerToken = sharedPreferences.getString("access_token", null)
//                val notifMs = viewModel.getNotificationMessages(bearerToken!!, 1, 3)
//                Log.d("notifMs", "notifMs: $notifMs")
//                _notificationMessage.value = notifMs
//            } catch (e: Exception) {
//                Log.d("notifMs", "Exception $e")
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun getDate(dateTime: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateTime1 = LocalDateTime.parse(dateTime, formatter)
        val localDate = dateTime1.toLocalDate()
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        val formattedDate = when {
            localDate == today -> stringResource(id = R.string.common_today)
            localDate == yesterday -> "${stringResource(id = R.string.common_yesterday)}, ${
                localDate.format(
                    DateTimeFormatter.ofPattern("d MMMM").withLocale(Locale.forLanguageTag("ru"))
                )
            }"

            else -> localDate.format(
                DateTimeFormatter.ofPattern("d MMMM").withLocale(Locale.forLanguageTag("ru"))
            )
        }.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        val fullFormattedDate = when {
            localDate == today || localDate == yesterday -> formattedDate
            else -> formattedDate
        }

        val date = sdf.parse(dateTime)
        val calendar = Calendar.getInstance()
        calendar.time = date ?: Date()

        return fullFormattedDate
    }
}

