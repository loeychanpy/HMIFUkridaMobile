package org.ukrida.hmifukridamobile.ui.login

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
import org.ukrida.hmifukridamobile.data.model.AuthResponse
import org.ukrida.hmifukridamobile.data.model.User
import org.ukrida.hmifukridamobile.data.repository.UserRepository

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var userRepo: UserRepository
    private lateinit var tokenManager: TokenManager
    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userRepo = mockk()
        tokenManager = mockk(relaxed = true)
        viewModel = LoginViewModel(userRepo, tokenManager)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun studentUser() = User(1, "Budi", "12345", "budi@test.com", "student")
    private fun adminUser()   = User(2, "Admin", "00000", "admin@test.com", "admin")

    // --- success path ---

    @Test
    fun `student login success sets UiState Success`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Success(
            AuthResponse("success", "tok123", studentUser(), null)
        )
        viewModel.login("budi@test.com", "pass123")
        assertTrue(viewModel.uiState is UiState.Success)
    }

    @Test
    fun `login success saves token to TokenManager`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Success(
            AuthResponse("success", "tok123", studentUser(), null)
        )
        viewModel.login("budi@test.com", "pass123")
        coVerify { tokenManager.saveToken("tok123") }
    }

    @Test
    fun `login success saves role to TokenManager`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Success(
            AuthResponse("success", "tok123", studentUser(), null)
        )
        viewModel.login("budi@test.com", "pass123")
        coVerify { tokenManager.saveRole("student") }
    }

    @Test
    fun `login success saves name to TokenManager`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Success(
            AuthResponse("success", "tok123", studentUser(), null)
        )
        viewModel.login("budi@test.com", "pass123")
        coVerify { tokenManager.saveName("Budi") }
    }

    @Test
    fun `login success saves userId to TokenManager`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Success(
            AuthResponse("success", "tok123", studentUser(), null)
        )
        viewModel.login("budi@test.com", "pass123")
        coVerify { tokenManager.saveUserId(1) }
    }

    @Test
    fun `admin login saves admin role`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Success(
            AuthResponse("success", "admintok", adminUser(), null)
        )
        viewModel.login("admin@test.com", "adminpass")
        coVerify { tokenManager.saveRole("admin") }
    }

    // --- failure path ---

    @Test
    fun `login failure sets UiState Error with message`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Error("Email atau password salah.")
        viewModel.login("wrong@test.com", "badpass")
        val state = viewModel.uiState
        assertTrue(state is UiState.Error)
        assertEquals("Email atau password salah.", (state as UiState.Error).message)
    }

    @Test
    fun `login failure does not save token`() = runTest {
        coEvery { userRepo.login(any(), any()) } returns UiState.Error("Gagal.")
        viewModel.login("wrong@test.com", "badpass")
        coVerify(exactly = 0) { tokenManager.saveToken(any()) }
    }

    // --- initial state ---

    @Test
    fun `initial uiState is null`() {
        assertNull(viewModel.uiState)
    }
}
