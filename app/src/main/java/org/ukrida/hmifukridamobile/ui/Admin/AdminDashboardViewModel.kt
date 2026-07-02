package org.ukrida.hmifukridamobile.ui.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.local.TokenManager
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.data.repository.EventRepository
import org.ukrida.hmifukridamobile.data.repository.UserRepository

class AdminDashboardViewModel(
    private val eventRepo: EventRepository,
    private val userRepo: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var eventsState by mutableStateOf<UiState<List<Event>>>(UiState.Loading)
        private set

    var userCount by mutableStateOf(0)
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: run {
                eventsState = UiState.Error("Sesi tidak ditemukan.")
                return@launch
            }
            eventsState = eventRepo.getEvents(token)
            val usersResult = userRepo.getAllUsers(token)
            if (usersResult is UiState.Success) {
                userCount = usersResult.data.size
            }
        }
    }

    companion object {
        fun factory(
            eventRepo: EventRepository,
            userRepo: UserRepository,
            tokenManager: TokenManager
        ) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                AdminDashboardViewModel(eventRepo, userRepo, tokenManager) as T
        }
    }
}
