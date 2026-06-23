package org.ukrida.hmifukridamobile.data.repository

import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.api.ApiService
import org.ukrida.hmifukridamobile.data.model.AuthResponse
import org.ukrida.hmifukridamobile.data.model.User

class UserRepository(private val api: ApiService) {

    suspend fun login(email: String, password: String): UiState<AuthResponse> {
        return try {
            val response = api.login(
                org.ukrida.hmifukridamobile.data.model.LoginRequest(email, password)
            )
            if (response.status == "success") {
                UiState.Success(response)
            } else {
                UiState.Error(response.message ?: "Login gagal.")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
        }
    }

    suspend fun register(name: String, nim: String, email: String, password: String): UiState<String> {
        return try {
            val response = api.register(
                org.ukrida.hmifukridamobile.data.model.RegisterRequest(name, nim, email, password)
            )
            if (response.status == "success") {
                UiState.Success(response.message ?: "Registrasi berhasil.")
            } else {
                UiState.Error(response.message ?: "Registrasi gagal.")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
        }
    }

    suspend fun logout(token: String): UiState<String> {
        return try {
            val response = api.logout("Bearer $token")
            if (response.status == "success") {
                UiState.Success(response.message ?: "Logout berhasil.")
            } else {
                UiState.Error(response.message ?: "Logout gagal.")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
        }
    }

    suspend fun getAllUsers(token: String): UiState<List<User>> {
        return try {
            val response = api.getAllUsers("Bearer $token")
            if (response.status == "success") {
                UiState.Success(response.data ?: emptyList())
            } else {
                UiState.Error(response.message ?: "Gagal memuat data pengguna.")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
        }
    }
}