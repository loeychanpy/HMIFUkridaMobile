package org.ukrida.hmifukridamobile.ui.admin

import io.mockk.coEvery
import io.mockk.coVerify
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
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.local.TokenManager
import org.ukrida.hmifukridamobile.data.repository.EventRepository

@OptIn(ExperimentalCoroutinesApi::class)
class AddEventViewModelTest {

    private lateinit var eventRepo: EventRepository
    private lateinit var tokenManager: TokenManager
    private lateinit var viewModel: AddEventViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        eventRepo = mockk()
        tokenManager = mockk(relaxed = true)
        viewModel = AddEventViewModel(eventRepo, tokenManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- token missing ---

    @Test
    fun `createEvent without token sets error asking to re-login`() = runTest {
        coEvery { tokenManager.getToken() } returns null
        viewModel.createEvent("Tech Talk", "2026-07-15", "09:00", "Desc", "Aula", "seminar", 100)
        val state = viewModel.uiState
        assertTrue(state is UiState.Error)
        assertTrue((state as UiState.Error).message.contains("login ulang"))
    }

    @Test
    fun `createEvent without token does not call repository`() = runTest {
        coEvery { tokenManager.getToken() } returns null
        viewModel.createEvent("Tech Talk", "2026-07-15", "09:00", "Desc", "Aula", "seminar", 100)
        coVerify(exactly = 0) { eventRepo.createEvent(any(), any(), any(), any(), any(), any(), any()) }
    }

    // --- success path ---

    @Test
    fun `createEvent with token calls repository and sets success`() = runTest {
        coEvery { tokenManager.getToken() } returns "tok123"
        coEvery { eventRepo.createEvent(any(), any(), any(), any(), any(), any(), any()) } returns UiState.Success("Event berhasil dibuat.")
        viewModel.createEvent("Tech Talk", "2026-07-15", "09:00", "Desc", "Aula", "seminar", 100)
        assertTrue(viewModel.uiState is UiState.Success)
    }

    @Test
    fun `createEvent concatenates date and time with space separator`() = runTest {
        coEvery { tokenManager.getToken() } returns "tok123"
        coEvery { eventRepo.createEvent(any(), any(), any(), any(), any(), any(), any()) } returns UiState.Success("OK")
        viewModel.createEvent("Event", "2026-07-20", "14:30", "Desc", "Ruang A", "workshop", 50)
        coVerify { eventRepo.createEvent(any(), any(), "2026-07-20 14:30", any(), any(), any(), any()) }
    }

    @Test
    fun `createEvent passes all fields correctly to repository`() = runTest {
        coEvery { tokenManager.getToken() } returns "tok123"
        coEvery { eventRepo.createEvent(any(), any(), any(), any(), any(), any(), any()) } returns UiState.Success("OK")
        viewModel.createEvent("Tech Talk", "2026-07-15", "09:00", "Discussion", "Aula", "seminar", 150)
        coVerify {
            eventRepo.createEvent(
                "tok123",
                "Tech Talk",
                "2026-07-15 09:00",
                "Discussion",
                "Aula",
                "seminar",
                150
            )
        }
    }

    // --- failure path ---

    @Test
    fun `createEvent repository failure sets UiState Error`() = runTest {
        coEvery { tokenManager.getToken() } returns "tok123"
        coEvery { eventRepo.createEvent(any(), any(), any(), any(), any(), any(), any()) } returns UiState.Error("Gagal membuat event.")
        viewModel.createEvent("Tech Talk", "2026-07-15", "09:00", "Desc", "Aula", "seminar", 100)
        assertTrue(viewModel.uiState is UiState.Error)
        assertEquals("Gagal membuat event.", (viewModel.uiState as UiState.Error).message)
    }

    // --- initial state ---

    @Test
    fun `initial uiState is null`() {
        assertNull(viewModel.uiState)
    }
}
