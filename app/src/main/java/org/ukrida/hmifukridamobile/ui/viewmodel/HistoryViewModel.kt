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

class HistoryViewModel(
    private val eventRepo: EventRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var historyState by mutableStateOf<UiState<List<Event>>>(UiState.Loading)
        private set

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: run {
                historyState = UiState.Error("Sesi tidak ditemukan. Silakan login ulang.")
                return@launch
            }
            historyState = eventRepo.getEventHistory(token)
        }
    }

    companion object {
        fun factory(eventRepo: EventRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    HistoryViewModel(eventRepo, tokenManager) as T
            }
    }
}
