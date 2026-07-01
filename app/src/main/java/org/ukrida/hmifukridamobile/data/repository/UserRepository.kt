package org.ukrida.hmifukridamobile.data.repository

import com.google.gson.JsonSyntaxException
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.api.ApiService
import org.ukrida.hmifukridamobile.data.model.AuthResponse
import org.ukrida.hmifukridamobile.data.model.User
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepository(private val api: ApiService) {

    private fun friendlyError(e: Exception): String = when (e) {
        is UnknownHostException -> "Tidak dapat terhubung ke server. Periksa koneksi internet Anda."
        is SocketTimeoutException -> "Koneksi ke server timeout. Silahkan coba lagi."
        is JsonSyntaxException -> "Terjadi kesalahan pada server. Silahkan coba lagi."
        else -> "Terjadi kesalahan. Silahkan coba lagi."
    }

    suspend fun login(email: String, password: String): UiState<AuthResponse> {
        return try {
            val response = api.login(
                org.ukrida.hmifukridamobile.data.model.LoginRequest(email, password)
            )
            if (response.status == "success") {
                UiState.Success(response)
            } else {
                UiState.Error(response.message ?: "Email atau password salah.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
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
            UiState.Error(friendlyError(e))
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
            UiState.Error(friendlyError(e))
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
            UiState.Error(friendlyError(e))
        }
    }
}