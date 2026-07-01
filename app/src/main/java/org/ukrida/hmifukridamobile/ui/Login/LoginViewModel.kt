package org.ukrida.hmifukridamobile.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.local.TokenManager
import org.ukrida.hmifukridamobile.data.model.AuthResponse
import org.ukrida.hmifukridamobile.data.repository.UserRepository

class LoginViewModel(
    private val userRepo: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var uiState by mutableStateOf<UiState<AuthResponse>?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState = UiState.Loading
            val result = userRepo.login(email, password)
            if (result is UiState.Success) {
                val auth = result.data
                auth.token?.let { tokenManager.saveToken(it) }
                auth.data?.role?.let { tokenManager.saveRole(it) }
                auth.data?.name?.let { tokenManager.saveName(it) }
                auth.data?.id?.let { tokenManager.saveUserId(it) }
            }
            uiState = result
        }
    }

    companion object {
        fun factory(userRepo: UserRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    LoginViewModel(userRepo, tokenManager) as T
            }
    }
}
