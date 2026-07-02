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
import org.ukrida.hmifukridamobile.data.model.AuthResponse
import org.ukrida.hmifukridamobile.data.model.User
import org.ukrida.hmifukridamobile.data.repository.UserRepository
import org.ukrida.hmifukridamobile.ui.login.LoginViewModel

/**
 * Functional tests for the login flow.
 * Uses a real DataStore-backed TokenManager (via instrumented context) + a mocked ApiService.
 * Verifies that a successful login correctly persists all session data.
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class LoginFlowFunctionalTest {

    private lateinit var api: ApiService
    private lateinit var tokenManager: TokenManager
    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        api = mockk()
        tokenManager = TokenManager(context)
        viewModel = LoginViewModel(UserRepository(api), tokenManager)
    }

    @After
    fun tearDown() = runTest {
        tokenManager.clear()
        Dispatchers.resetMain()
    }

    // --- student login ---

    @Test
    fun studentLoginFlow_tokenPersistedToDataStore() = runTest {
        val user = User(1, "Budi", "12345", "budi@test.com", "student")
        coEvery { api.login(any()) } returns AuthResponse("success", "student_tok_abc", user, null)

        viewModel.login("budi@test.com", "pass123")

        assertTrue(viewModel.uiState is UiState.Success)
        assertEquals("student_tok_abc", tokenManager.getToken())
    }

    @Test
    fun studentLoginFlow_rolePersistedToDataStore() = runTest {
        val user = User(1, "Budi", "12345", "budi@test.com", "student")
        coEvery { api.login(any()) } returns AuthResponse("success", "student_tok_abc", user, null)

        viewModel.login("budi@test.com", "pass123")

        assertEquals("student", tokenManager.getRole())
    }

    @Test
    fun studentLoginFlow_nameAndUserIdPersistedToDataStore() = runTest {
        val user = User(1, "Budi", "12345", "budi@test.com", "student")
        coEvery { api.login(any()) } returns AuthResponse("success", "student_tok_abc", user, null)

        viewModel.login("budi@test.com", "pass123")

        assertEquals("Budi", tokenManager.getName())
        assertEquals(1, tokenManager.getUserId())
    }

    // --- admin login ---

    @Test
    fun adminLoginFlow_adminRolePersistedToDataStore() = runTest {
        val admin = User(2, "Admin", "00000", "admin@test.com", "admin")
        coEvery { api.login(any()) } returns AuthResponse("success", "admin_tok_xyz", admin, null)

        viewModel.login("admin@test.com", "adminpass")

        assertTrue(viewModel.uiState is UiState.Success)
        assertEquals("admin", tokenManager.getRole())
        assertEquals("admin_tok_xyz", tokenManager.getToken())
    }

    // --- failed login ---

    @Test
    fun failedLoginFlow_dataStoreRemainsEmpty() = runTest {
        coEvery { api.login(any()) } returns AuthResponse("error", null, null, "Kredensial tidak valid.")

        viewModel.login("bad@test.com", "badpass")

        assertTrue(viewModel.uiState is UiState.Error)
        assertNull(tokenManager.getToken())
        assertNull(tokenManager.getRole())
    }

    @Test
    fun failedLoginFlow_errorMessagePropagatedToUiState() = runTest {
        coEvery { api.login(any()) } returns AuthResponse("error", null, null, "Email atau password salah.")

        viewModel.login("bad@test.com", "badpass")

        val state = viewModel.uiState
        assertTrue(state is UiState.Error)
        assertEquals("Email atau password salah.", (state as UiState.Error).message)
    }

    // --- logout after login ---

    @Test
    fun logoutFlow_dataStoreClearedAfterLogout() = runTest {
        val user = User(1, "Budi", "12345", "budi@test.com", "student")
        coEvery { api.login(any()) } returns AuthResponse("success", "tok123", user, null)
        coEvery { api.logout(any()) } returns org.ukrida.hmifukridamobile.data.model.ApiResponse("success", null, "Logout berhasil.")

        viewModel.login("budi@test.com", "pass123")
        assertEquals("tok123", tokenManager.getToken())

        val userRepo = UserRepository(api)
        userRepo.logout("tok123")
        tokenManager.clear()

        assertNull(tokenManager.getToken())
        assertNull(tokenManager.getRole())
    }
}
