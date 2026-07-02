package org.ukrida.hmifukridamobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
            coroutineScope {
                val eventDeferred      = async { eventRepo.getEventById(token, eventId) }
                val registrantsDeferred = async { eventRepo.getEventRegistrants(token, eventId) }
                eventState      = eventDeferred.await()
                registrantsState = registrantsDeferred.await()
            }
        }
    }

    var checkInMessage by mutableStateOf<String?>(null)
        private set

    fun checkInByQr(registrationId: Int) {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: return@launch
            when (val result = eventRepo.checkIn(token, registrationId)) {
                is UiState.Success -> {
                    checkInMessage = result.data
                    val current = (registrantsState as? UiState.Success)?.data ?: return@launch
                    registrantsState = UiState.Success(
                        current.map {
                            if (it.registrationId == registrationId) it.copy(attended = true) else it
                        }
                    )
                }
                is UiState.Error -> checkInMessage = result.message
                else -> {}
            }
        }
    }

    fun clearCheckInMessage() { checkInMessage = null }

    fun markAttendance(registrationId: Int, attended: Boolean) {
        val current = (registrantsState as? UiState.Success)?.data ?: return
        registrantsState = UiState.Success(
            current.map { if (it.registrationId == registrationId) it.copy(attended = attended) else it }
        )
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: return@launch
            val result = eventRepo.markAttendance(token, registrationId, attended)
            if (result is UiState.Error) {
                registrantsState = UiState.Success(current)
            }
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
