package org.ukrida.hmifukridamobile.ui.register

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
import org.ukrida.hmifukridamobile.UiState
import org.ukrida.hmifukridamobile.data.repository.UserRepository

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    private lateinit var userRepo: UserRepository
    private lateinit var viewModel: RegisterViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userRepo = mockk()
        viewModel = RegisterViewModel(userRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- validation (synchronous, exits before API call) ---

    @Test
    fun `empty name sets error - semua kolom harus diisi`() {
        viewModel.register("", "12345", "a@b.com", "pass123", "pass123", true)
        assertError(viewModel.uiState, "Semua kolom harus diisi.")
    }

    @Test
    fun `empty NIM sets error`() {
        viewModel.register("Janis", "", "a@b.com", "pass123", "pass123", true)
        assertError(viewModel.uiState, "Semua kolom harus diisi.")
    }

    @Test
    fun `empty email sets error`() {
        viewModel.register("Janis", "12345", "", "pass123", "pass123", true)
        assertError(viewModel.uiState, "Semua kolom harus diisi.")
    }

    @Test
    fun `empty password sets error`() {
        viewModel.register("Janis", "12345", "a@b.com", "", "pass123", true)
        assertError(viewModel.uiState, "Semua kolom harus diisi.")
    }

    @Test
    fun `empty confirm password sets error`() {
        viewModel.register("Janis", "12345", "a@b.com", "pass123", "", true)
        assertError(viewModel.uiState, "Semua kolom harus diisi.")
    }

    @Test
    fun `password mismatch sets error`() {
        viewModel.register("Janis", "12345", "a@b.com", "pass123", "different", true)
        assertError(viewModel.uiState, "Password dan konfirmasi password tidak sama.")
    }

    @Test
    fun `no agreement sets error`() {
        viewModel.register("Janis", "12345", "a@b.com", "pass123", "pass123", false)
        assertError(viewModel.uiState, "Anda harus menyetujui syarat & ketentuan.")
    }

    // --- happy path (calls repository) ---

    @Test
    fun `valid input calls repository and reflects success state`() = runTest {
        coEvery { userRepo.register(any(), any(), any(), any()) } returns UiState.Success("Registrasi berhasil.")
        viewModel.register("Janis", "12345", "a@b.com", "pass123", "pass123", true)
        assertTrue(viewModel.uiState is UiState.Success)
    }

    @Test
    fun `valid input with repository error reflects error state`() = runTest {
        coEvery { userRepo.register(any(), any(), any(), any()) } returns UiState.Error("Email sudah digunakan.")
        viewModel.register("Janis", "12345", "taken@b.com", "pass123", "pass123", true)
        assertError(viewModel.uiState, "Email sudah digunakan.")
    }

    // --- initial state ---

    @Test
    fun `initial uiState is null`() {
        assertNull(viewModel.uiState)
    }

    private fun assertError(state: UiState<*>?, expectedMessage: String) {
        assertTrue("Expected UiState.Error but was $state", state is UiState.Error)
        assertEquals(expectedMessage, (state as UiState.Error).message)
    }
}
