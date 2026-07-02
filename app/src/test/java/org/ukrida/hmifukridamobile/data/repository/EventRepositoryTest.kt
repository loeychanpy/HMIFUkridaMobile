package org.ukrida.hmifukridamobile.data.repository

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.api.ApiService
import org.ukrida.hmifukridamobile.data.model.ApiResponse
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.data.model.EventRegistrant
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class EventRepositoryTest {

    private lateinit var api: ApiService
    private lateinit var repo: EventRepository

    @Before
    fun setup() {
        api = mockk()
        repo = EventRepository(api)
    }

    private val token = "tok123"

    private fun fakeEvent(id: Int = 1) =
        Event(id, "Tech Talk", "Discussion", "Aula", "seminar", 100, "2026-07-15 09:00", 1, "2026-07-01 00:00:00")

    private fun fakeRegistrant() =
        EventRegistrant(1, "Budi", "11111", "budi@test.com", "2026-07-01 00:00:00")

    // --- getEvents ---

    @Test
    fun `getEvents success returns full event list`() = runTest {
        val events = listOf(fakeEvent(1), fakeEvent(2))
        coEvery { api.getEvents(any()) } returns ApiResponse("success", events, null)
        val result = repo.getEvents(token)
        assertTrue(result is UiState.Success)
        assertEquals(2, (result as UiState.Success).data.size)
    }

    @Test
    fun `getEvents success with null data returns empty list`() = runTest {
        coEvery { api.getEvents(any()) } returns ApiResponse("success", null, null)
        val result = repo.getEvents(token)
        assertTrue(result is UiState.Success)
        assertTrue((result as UiState.Success).data.isEmpty())
    }

    @Test
    fun `getEvents server error returns UiState Error`() = runTest {
        coEvery { api.getEvents(any()) } returns ApiResponse("error", null, "Gagal memuat event.")
        val result = repo.getEvents(token)
        assertTrue(result is UiState.Error)
        assertEquals("Gagal memuat event.", (result as UiState.Error).message)
    }

    @Test
    fun `getEvents UnknownHostException returns internet message`() = runTest {
        coEvery { api.getEvents(any()) } throws UnknownHostException()
        val result = repo.getEvents(token)
        assertTrue(result is UiState.Error)
        assertTrue((result as UiState.Error).message.contains("internet"))
    }

    @Test
    fun `getEvents SocketTimeoutException returns timeout message`() = runTest {
        coEvery { api.getEvents(any()) } throws SocketTimeoutException()
        val result = repo.getEvents(token)
        assertTrue(result is UiState.Error)
        assertTrue((result as UiState.Error).message.contains("timeout"))
    }

    // --- getEventById ---

    @Test
    fun `getEventById success returns correct event`() = runTest {
        coEvery { api.getEventById(any(), 1) } returns ApiResponse("success", fakeEvent(1), null)
        val result = repo.getEventById(token, 1)
        assertTrue(result is UiState.Success)
        assertEquals("Tech Talk", (result as UiState.Success).data.title)
        assertEquals(1, result.data.id)
    }

    @Test
    fun `getEventById not found returns UiState Error`() = runTest {
        coEvery { api.getEventById(any(), 999) } returns ApiResponse("error", null, "Event tidak ditemukan.")
        val result = repo.getEventById(token, 999)
        assertTrue(result is UiState.Error)
        assertEquals("Event tidak ditemukan.", (result as UiState.Error).message)
    }

    @Test
    fun `getEventById success with null data returns error`() = runTest {
        coEvery { api.getEventById(any(), any()) } returns ApiResponse("success", null, null)
        val result = repo.getEventById(token, 1)
        assertTrue(result is UiState.Error)
    }

    // --- createEvent ---

    @Test
    fun `createEvent success returns UiState Success`() = runTest {
        coEvery { api.createEvent(any(), any()) } returns ApiResponse("success", null, "Event berhasil dibuat.")
        val result = repo.createEvent(token, "Tech Talk", "2026-07-15 09:00", "Desc", "Aula", "seminar", 150)
        assertTrue(result is UiState.Success)
        assertEquals("Event berhasil dibuat.", (result as UiState.Success).data)
    }

    @Test
    fun `createEvent server error returns UiState Error`() = runTest {
        coEvery { api.createEvent(any(), any()) } returns ApiResponse("error", null, "Gagal membuat event.")
        val result = repo.createEvent(token, "Tech Talk", "2026-07-15 09:00", "Desc", "Aula", "seminar", 150)
        assertTrue(result is UiState.Error)
    }

    @Test
    fun `createEvent network error returns friendly message`() = runTest {
        coEvery { api.createEvent(any(), any()) } throws UnknownHostException()
        val result = repo.createEvent(token, "Tech Talk", "2026-07-15 09:00", "Desc", "Aula", "seminar", 150)
        assertTrue(result is UiState.Error)
        assertTrue((result as UiState.Error).message.contains("internet"))
    }

    // --- registerForEvent ---

    @Test
    fun `registerForEvent success returns UiState Success`() = runTest {
        coEvery { api.registerForEvent(any(), any()) } returns ApiResponse("success", null, "Berhasil mendaftar ke event.")
        val result = repo.registerForEvent(token, 1)
        assertTrue(result is UiState.Success)
    }

    @Test
    fun `registerForEvent already registered returns UiState Error`() = runTest {
        coEvery { api.registerForEvent(any(), any()) } returns ApiResponse("error", null, "Sudah mendaftar.")
        val result = repo.registerForEvent(token, 1)
        assertTrue(result is UiState.Error)
        assertEquals("Sudah mendaftar.", (result as UiState.Error).message)
    }

    // --- getEventRegistrants ---

    @Test
    fun `getEventRegistrants success returns registrant list`() = runTest {
        coEvery { api.getEventRegistrants(any(), 1) } returns ApiResponse("success", listOf(fakeRegistrant()), null)
        val result = repo.getEventRegistrants(token, 1)
        assertTrue(result is UiState.Success)
        assertEquals(1, (result as UiState.Success).data.size)
        assertEquals("Budi", result.data[0].name)
    }

    @Test
    fun `getEventRegistrants empty event returns empty list`() = runTest {
        coEvery { api.getEventRegistrants(any(), any()) } returns ApiResponse("success", null, null)
        val result = repo.getEventRegistrants(token, 2)
        assertTrue(result is UiState.Success)
        assertTrue((result as UiState.Success).data.isEmpty())
    }

    // --- getEventHistory ---

    @Test
    fun `getEventHistory success returns user registered events`() = runTest {
        coEvery { api.getEventHistory(any()) } returns ApiResponse("success", listOf(fakeEvent()), null)
        val result = repo.getEventHistory(token)
        assertTrue(result is UiState.Success)
        assertEquals(1, (result as UiState.Success).data.size)
    }

    @Test
    fun `getEventHistory empty returns empty list`() = runTest {
        coEvery { api.getEventHistory(any()) } returns ApiResponse("success", null, null)
        val result = repo.getEventHistory(token)
        assertTrue(result is UiState.Success)
        assertTrue((result as UiState.Success).data.isEmpty())
    }

    // --- deleteEvent ---

    @Test
    fun `deleteEvent success returns UiState Success`() = runTest {
        coEvery { api.deleteEvent(any(), any()) } returns ApiResponse("success", null, "Event berhasil dihapus.")
        val result = repo.deleteEvent(token, 1)
        assertTrue(result is UiState.Success)
    }

    @Test
    fun `deleteEvent not found returns UiState Error`() = runTest {
        coEvery { api.deleteEvent(any(), any()) } returns ApiResponse("error", null, "Event tidak ditemukan.")
        val result = repo.deleteEvent(token, 999)
        assertTrue(result is UiState.Error)
    }
}
