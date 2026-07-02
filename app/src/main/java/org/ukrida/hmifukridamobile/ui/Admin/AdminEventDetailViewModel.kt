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
import org.ukrida.hmifukridamobile.data.model.EventRegistrant
import org.ukrida.hmifukridamobile.data.repository.EventRepository

class AdminEventDetailViewModel(
    private val eventId: Int,
    private val eventRepo: EventRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var eventState by mutableStateOf<UiState<Event>>(UiState.Loading)
        private set

    var registrantsState by mutableStateOf<UiState<List<EventRegistrant>>>(UiState.Loading)
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: run {
                eventState = UiState.Error("Sesi tidak ditemukan.")
                registrantsState = UiState.Error("Sesi tidak ditemukan.")
                return@launch
            }
            eventState = eventRepo.getEventById(token, eventId)
            registrantsState = eventRepo.getEventRegistrants(token, eventId)
        }
    }

    companion object {
        fun factory(eventId: Int, eventRepo: EventRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    AdminEventDetailViewModel(eventId, eventRepo, tokenManager) as T
            }
    }
}
