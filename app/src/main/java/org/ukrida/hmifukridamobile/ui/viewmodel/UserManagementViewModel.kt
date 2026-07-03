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
import org.ukrida.hmifukridamobile.data.model.User
import org.ukrida.hmifukridamobile.data.repository.UserRepository

class UserManagementViewModel(
    private val userRepo: UserRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    var usersState by mutableStateOf<UiState<List<User>>>(UiState.Loading)
        private set

    var snackbarMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: run {
                usersState = UiState.Error("Sesi tidak ditemukan. Silakan login ulang.")
                return@launch
            }
            usersState = userRepo.getAllUsers(token)
        }
    }

    fun deleteUser(userId: Int) {
        val current = (usersState as? UiState.Success)?.data ?: return
        usersState = UiState.Success(current.filter { it.id != userId })
        viewModelScope.launch {
            val token = tokenManager.getToken() ?: return@launch
            val result = userRepo.deleteUser(token, userId)
            if (result is UiState.Error) {
                usersState = UiState.Success(current)
                snackbarMessage = result.message
            } else if (result is UiState.Success) {
                snackbarMessage = result.data
            }
        }
    }

    fun clearSnackbar() { snackbarMessage = null }

    companion object {
        fun factory(userRepo: UserRepository, tokenManager: TokenManager) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    UserManagementViewModel(userRepo, tokenManager) as T
            }
    }
}
