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
import org.ukrida.hmifukridamobile.data.repository.EventRepository

class AddEventViewModel(
    private val eventRepo: EventRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var uiState by mutableStateOf<UiState<String>?>(null)
        private set

    fun createEvent(
        title: String,
        date: String,
        time: String,
        description: String,
        location: String
    ) {
        viewModelScope.launch {
            uiState = UiState.Loading
            val token = tokenManager.getToken() ?: run {
                uiState = UiState.Error("Sesi tidak ditemukan. Silakan login ulang.")
                return@launch
            }
            val eventDate = "$date $time"
            uiState = eventRepo.createEvent(token, title, eventDate, description, location)
        }
    }

    companion object {
        fun factory(eventRepo: EventRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    AddEventViewModel(eventRepo, tokenManager) as T
            }
    }
}
