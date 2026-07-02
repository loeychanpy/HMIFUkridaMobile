package org.ukrida.hmifukridamobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.repository.UserRepository

class RegisterViewModel(
    private val userRepo: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf<UiState<String>?>(null)
        private set

    fun register(name: String, nim: String, email: String, password: String, confirmPassword: String, agree: Boolean) {
        when {
            name.isBlank() || nim.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                uiState = UiState.Error("Semua kolom harus diisi.")
                return
            }
            !email.endsWith("@civitas.ukrida.ac.id") -> {
                uiState = UiState.Error("Email harus menggunakan domain @civitas.ukrida.ac.id.")
                return
            }
            password != confirmPassword -> {
                uiState = UiState.Error("Password dan konfirmasi password tidak sama.")
                return
            }
            !agree -> {
                uiState = UiState.Error("Anda harus menyetujui syarat & ketentuan.")
                return
            }
        }
        viewModelScope.launch {
            uiState = UiState.Loading
            uiState = userRepo.register(name, nim, email, password)
        }
    }

    companion object {
        fun factory(userRepo: UserRepository) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    RegisterViewModel(userRepo) as T
            }
    }
}
