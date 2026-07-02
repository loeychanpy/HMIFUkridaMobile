package org.ukrida.hmifukridamobile

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.ukrida.hmifukridamobile.data.api.ApiService
import org.ukrida.hmifukridamobile.data.local.TokenManager
import org.ukrida.hmifukridamobile.data.model.ApiResponse
import org.ukrida.hmifukridamobile.data.model.Event
import org.ukrida.hmifukridamobile.data.model.EventRegistrant
import org.ukrida.hmifukridamobile.data.repository.EventRepository
import org.ukrida.hmifukridamobile.ui.admin.AddEventViewModel

/**
 * Functional tests for event flows.
 * Uses a real DataStore-backed TokenManager + a mocked ApiService to verify
 * that session state drives the correct behaviour in AddEventViewModel.
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class EventFlowFunctionalTest {

    private lateinit var api: ApiService
    private lateinit var tokenManager: TokenManager
    private lateinit var repo: EventRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        api = mockk()
        tokenManager = TokenManager(context)
        repo = EventRepository(api)
    }

    @After
    fun tearDown() = runTest {
        tokenManager.clear()
        Dispatchers.resetMain()
    }

    private fun fakeEvent(id: Int = 1) =
        Event(id, "Tech Talk", "Discussion", "Aula", "2026-07-15 09:00", 2, "2026-07-01 00:00:00")

    private fun fakeRegistrant() =
        EventRegistrant(1, "Budi", "12345", "budi@test.com", "2026-07-01 00:00:00")

    // --- AddEvent flow: admin already logged in ---

    @Test
    fun addEventFlow_adminWithSavedTokenCanCreateEvent() = runTest {
        tokenManager.saveToken("admin_tok")
        coEvery { api.createEvent(any(), any()) } returns ApiResponse("success", null, "Event berhasil dibuat.")

        val viewModel = AddEventViewModel(repo, tokenManager)
        viewModel.createEvent("Workshop Kotlin", "2026-07-20", "10:00", "Belajar Kotlin", "Ruang A", "workshop")

        assertTrue(viewModel.uiState is UiState.Success)
        assertEquals("Event berhasil dibuat.", (viewModel.uiState as UiState.Success).data)
    }

    @Test
    fun addEventFlow_noSavedTokenShowsSessionError() = runTest {
        // DataStore is cleared — no token present
        val viewModel = AddEventViewModel(repo, tokenManager)
        viewModel.createEvent("Workshop Kotlin", "2026-07-20", "10:00", "Desc", "Ruang A", "workshop")

        assertTrue(viewModel.uiState is UiState.Error)
        assertTrue((viewModel.uiState as UiState.Error).message.contains("login ulang"))
    }

    // --- Student event registration flow ---

    @Test
    fun registerForEventFlow_successReturnsSuccess() = runTest {
        tokenManager.saveToken("student_tok")
        coEvery { api.registerForEvent(any(), any()) } returns ApiResponse("success", null, "Berhasil mendaftar ke event.")

        val token = tokenManager.getToken()!!
        val result = repo.registerForEvent(token, 5)

        assertTrue(result is UiState.Success)
    }

    @Test
    fun registerForEventFlow_alreadyRegisteredReturnsError() = runTest {
        tokenManager.saveToken("student_tok")
        coEvery { api.registerForEvent(any(), any()) } returns ApiResponse("error", null, "Sudah mendaftar.")

        val token = tokenManager.getToken()!!
        val result = repo.registerForEvent(token, 5)

        assertTrue(result is UiState.Error)
        assertEquals("Sudah mendaftar.", (result as UiState.Error).message)
    }

    // --- Event listing flow ---

    @Test
    fun getEventsFlow_returnsEventsForAuthenticatedUser() = runTest {
        tokenManager.saveToken("student_tok")
        val events = listOf(fakeEvent(1), fakeEvent(2))
        coEvery { api.getEvents(any()) } returns ApiResponse("success", events, null)

        val token = tokenManager.getToken()!!
        val result = repo.getEvents(token)

        assertTrue(result is UiState.Success)
        assertEquals(2, (result as UiState.Success).data.size)
    }

    // --- Admin registrant view flow ---

    @Test
    fun getEventRegistrantsFlow_adminCanViewRegistrants() = runTest {
        tokenManager.saveToken("admin_tok")
        coEvery { api.getEventRegistrants(any(), 3) } returns ApiResponse("success", listOf(fakeRegistrant()), null)

        val token = tokenManager.getToken()!!
        val result = repo.getEventRegistrants(token, 3)

        assertTrue(result is UiState.Success)
        assertEquals("Budi", (result as UiState.Success).data[0].name)
    }

    // --- Event history flow ---

    @Test
    fun getEventHistoryFlow_showsRegisteredEventsForUser() = runTest {
        tokenManager.saveToken("student_tok")
        coEvery { api.getEventHistory(any()) } returns ApiResponse("success", listOf(fakeEvent()), null)

        val token = tokenManager.getToken()!!
        val result = repo.getEventHistory(token)

        assertTrue(result is UiState.Success)
        assertEquals(1, (result as UiState.Success).data.size)
        assertEquals("Tech Talk", result.data[0].title)
    }
}
