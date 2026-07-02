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

class EditEventViewModel(
    private val eventId: Int,
    private val eventRepo: EventRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var eventState by mutableStateOf<UiState<Event>>(UiState.Loading)
        private set

    var updateState by mutableStateOf<UiState<String>?>(null)
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

    fun updateEvent(
        title: String,
        date: String,
        time: String,
        description: String,
        location: String
    ) {
        viewModelScope.launch {
            updateState = UiState.Loading
            val token = tokenManager.getToken() ?: run {
                updateState = UiState.Error("Sesi tidak ditemukan. Silakan login ulang.")
                return@launch
            }
            val eventDate = "$date $time"
            updateState = eventRepo.updateEvent(token, eventId, title, eventDate, description, location)
        }
    }

    companion object {
        fun factory(eventId: Int, eventRepo: EventRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    EditEventViewModel(eventId, eventRepo, tokenManager) as T
            }
    }
}
