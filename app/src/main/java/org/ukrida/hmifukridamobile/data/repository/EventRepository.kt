package org.ukrida.hmifukridamobile.data.repository

import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.api.ApiService
import org.ukrida.hmifukridamobile.data.model.Announcement
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.data.model.EventRegistrant

class EventRepository(private val api: ApiService) {

    suspend fun getEvents(token: String): UiState<List<Event>> {
        return try {
            val response = api.getEvents("Bearer $token")
            if (response.status == "success") {
                UiState.Success(response.data ?: emptyList())
            } else {
                UiState.Error(response.message ?: "Gagal memuat event.")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
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
            UiState.Error(e.message ?: "Terjadi kesalahan jaringan.")
        }
    }
}
