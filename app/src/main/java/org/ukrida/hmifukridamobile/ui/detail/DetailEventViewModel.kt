package org.ukrida.hmifukridamobile.ui.detail

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

class DetailEventViewModel(
    private val eventId: Int,
    private val eventRepo: EventRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var eventState by mutableStateOf<UiState<Event>>(UiState.Loading)
        private set

    var registerState by mutableStateOf<UiState<String>?>(null)
        private set

    init {
        loadEvent()
    }

    private fun loadEvent() {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: run {
                eventState = UiState.Error("Sesi tidak ditemukan. Silakan login ulang.")
                return@launch
            }
            eventState = eventRepo.getEventById(token, eventId)
        }
    }

    fun registerForEvent() {
        viewModelScope.launch {
            registerState = UiState.Loading
            val token = tokenManager.getToken() ?: run {
                registerState = UiState.Error("Sesi tidak ditemukan. Silakan login ulang.")
                return@launch
            }
            registerState = eventRepo.registerForEvent(token, eventId)
        }
    }

    companion object {
        fun factory(eventId: Int, eventRepo: EventRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    DetailEventViewModel(eventId, eventRepo, tokenManager) as T
            }
    }
}
