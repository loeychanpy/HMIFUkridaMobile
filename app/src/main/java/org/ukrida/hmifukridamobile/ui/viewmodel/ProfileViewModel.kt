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
import org.ukrida.hmifukridamobile.data.repository.UserRepository

class ProfileViewModel(
    private val userRepo: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var userName by mutableStateOf("")
        private set

    var logoutState by mutableStateOf<UiState<String>?>(null)
        private set

    init {
        loadUserName()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            userName = tokenManager.getName() ?: ""
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutState = UiState.Loading
            val token = tokenManager.getToken()
            if (token != null) {
                userRepo.logout(token)
            }
            tokenManager.clear()
            logoutState = UiState.Success("Logout berhasil.")
        }
    }

    companion object {
        fun factory(userRepo: UserRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    ProfileViewModel(userRepo, tokenManager) as T
            }
    }
}
