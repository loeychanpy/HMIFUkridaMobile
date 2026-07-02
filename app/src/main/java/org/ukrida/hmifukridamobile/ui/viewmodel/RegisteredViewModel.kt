package org.ukrida.hmifukridamobile.ui.viewmodel

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

class RegisteredViewModel(
    private val eventRepo: EventRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var eventsState by mutableStateOf<UiState<List<Event>>>(UiState.Loading)
        private set

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: run {
                eventsState = UiState.Error("Sesi tidak ditemukan. Silakan login ulang.")
                return@launch
            }
            eventsState = eventRepo.getMyRegistrations(token)
        }
    }

    companion object {
        fun factory(eventRepo: EventRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    RegisteredViewModel(eventRepo, tokenManager) as T
            }
    }
}
