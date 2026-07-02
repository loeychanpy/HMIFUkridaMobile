package org.ukrida.hmifukridamobile.data.repository

import com.google.gson.JsonSyntaxException
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.api.ApiService
import org.ukrida.hmifukridamobile.data.model.Announcement
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.data.model.EventRegistrant
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class EventRepository(private val api: ApiService) {

    private fun friendlyError(e: Exception): String = when (e) {
        is UnknownHostException -> "Tidak dapat terhubung ke server. Periksa koneksi internet Anda."
        is SocketTimeoutException -> "Koneksi ke server timeout. Silahkan coba lagi."
        is JsonSyntaxException -> "Terjadi kesalahan pada server. Silahkan coba lagi."
        else -> "Terjadi kesalahan. Silahkan coba lagi."
    }

    suspend fun getEvents(token: String): UiState<List<Event>> {
        return try {
            val response = api.getEvents("Bearer $token")
            if (response.status == "success") {
                UiState.Success(response.data ?: emptyList())
            } else {
                UiState.Error(response.message ?: "Gagal memuat event.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun getEventById(token: String, id: Int): UiState<Event> {
        return try {
            val response = api.getEventById("Bearer $token", id)
            if (response.status == "success" && response.data != null) {
                UiState.Success(response.data)
            } else {
                UiState.Error(response.message ?: "Event tidak ditemukan.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun getMyRegistrations(token: String): UiState<List<Event>> {
        return try {
            val response = api.getMyRegistrations("Bearer $token")
            if (response.status == "success") {
                UiState.Success(response.data ?: emptyList())
            } else {
                UiState.Error(response.message ?: "Belum ada event yang didaftarkan.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun getEventHistory(token: String): UiState<List<Event>> {
        return try {
            val response = api.getEventHistory("Bearer $token")
            if (response.status == "success") {
                UiState.Success(response.data ?: emptyList())
            } else {
                UiState.Error(response.message ?: "Belum ada histori acara.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun createEvent(
        token: String,
        title: String,
        eventDate: String,
        description: String,
        location: String
    ): UiState<String> {
        return try {
            val body = mapOf(
                "title"       to title,
                "event_date"  to eventDate,
                "description" to description,
                "location"    to location
            )
            val response = api.createEvent("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Event berhasil dibuat.")
            } else {
                UiState.Error(response.message ?: "Gagal membuat event.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun updateEvent(
        token: String,
        id: Int,
        title: String,
        eventDate: String,
        description: String,
        location: String
    ): UiState<String> {
        return try {
            val body = mapOf(
                "id"          to id.toString(),
                "title"       to title,
                "event_date"  to eventDate,
                "description" to description,
                "location"    to location
            )
            val response = api.updateEvent("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Event berhasil diperbarui.")
            } else {
                UiState.Error(response.message ?: "Gagal memperbarui event.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun deleteEvent(token: String, eventId: Int): UiState<String> {
        return try {
            val body = mapOf("event_id" to eventId)
            val response = api.deleteEvent("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Event berhasil dihapus.")
            } else {
                UiState.Error(response.message ?: "Gagal menghapus event.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun registerForEvent(token: String, eventId: Int): UiState<String> {
        return try {
            val body = mapOf("event_id" to eventId)
            val response = api.registerForEvent("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Berhasil mendaftar ke event.")
            } else {
                UiState.Error(response.message ?: "Gagal mendaftar ke event.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun getEventRegistrants(token: String, eventId: Int): UiState<List<EventRegistrant>> {
        return try {
            val response = api.getEventRegistrants("Bearer $token", eventId)
            if (response.status == "success") {
                UiState.Success(response.data ?: emptyList())
            } else {
                UiState.Error(response.message ?: "Gagal memuat peserta.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun checkIn(token: String, registrationId: Int): UiState<String> {
        return try {
            val body = mapOf("registration_id" to registrationId)
            val response = api.checkIn("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Kehadiran berhasil dicatat.")
            } else {
                UiState.Error(response.message ?: "Gagal mencatat kehadiran.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun markAttendance(token: String, registrationId: Int, attended: Boolean): UiState<String> {
        return try {
            val body = mapOf<String, Any>("registration_id" to registrationId, "attended" to if (attended) 1 else 0)
            val response = api.markAttendance("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Kehadiran berhasil diperbarui.")
            } else {
                UiState.Error(response.message ?: "Gagal memperbarui kehadiran.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun getAnnouncements(token: String): UiState<List<Announcement>> {
        return try {
            val response = api.getAnnouncements("Bearer $token")
            if (response.status == "success") {
                UiState.Success(response.data ?: emptyList())
            } else {
                UiState.Error(response.message ?: "Gagal memuat pengumuman.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun createAnnouncement(token: String, title: String, content: String): UiState<String> {
        return try {
            val body = mapOf("title" to title, "content" to content)
            val response = api.createAnnouncement("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Pengumuman berhasil dibuat.")
            } else {
                UiState.Error(response.message ?: "Gagal membuat pengumuman.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }

    suspend fun deleteAnnouncement(token: String, announcementId: Int): UiState<String> {
        return try {
            val body = mapOf("announcement_id" to announcementId)
            val response = api.deleteAnnouncement("Bearer $token", body)
            if (response.status == "success") {
                UiState.Success(response.message ?: "Pengumuman berhasil dihapus.")
            } else {
                UiState.Error(response.message ?: "Gagal menghapus pengumuman.")
            }
        } catch (e: Exception) {
            UiState.Error(friendlyError(e))
        }
    }
}
