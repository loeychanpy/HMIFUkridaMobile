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
import org.ukrida.hmifukridamobile.data.model.AuthResponse
import org.ukrida.hmifukridamobile.data.model.User
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class UserRepositoryTest {

    private lateinit var api: ApiService
    private lateinit var repo: UserRepository

    @Before
    fun setup() {
        api = mockk()
        repo = UserRepository(api)
    }

    private fun fakeUser() = User(1, "Janis", "12345", "janis@test.com", "student")
    private fun fakeAuthSuccess() = AuthResponse("success", "tok123", fakeUser(), null)
    private fun fakeAuthError() = AuthResponse("error", null, null, "Email atau password salah.")

    // --- login ---

    @Test
    fun `login success returns UiState Success with token`() = runTest {
        coEvery { api.login(any()) } returns fakeAuthSuccess()
        val result = repo.login("janis@test.com", "pass123")
        assertTrue(result is UiState.Success)
        assertEquals("tok123", (result as UiState.Success).data.token)
    }

    @Test
    fun `login server error returns UiState Error with server message`() = runTest {
        coEvery { api.login(any()) } returns fakeAuthError()
        val result = repo.login("janis@test.com", "wrongpass")
        assertTrue(result is UiState.Error)
        assertEquals("Email atau password salah.", (result as UiState.Error).message)
    }

    @Test
    fun `login with null message falls back to default error text`() = runTest {
        coEvery { api.login(any()) } returns AuthResponse("error", null, null, null)
        val result = repo.login("a@b.com", "p")
        assertTrue(result is UiState.Error)
        assertFalse((result as UiState.Error).message.isBlank())
    }

    @Test
    fun `login UnknownHostException returns internet connectivity message`() = runTest {
        coEvery { api.login(any()) } throws UnknownHostException()
        val result = repo.login("a@b.com", "p")
        assertTrue(result is UiState.Error)
        assertTrue((result as UiState.Error).message.contains("internet"))
    }

    @Test
    fun `login SocketTimeoutException returns timeout message`() = runTest {
        coEvery { api.login(any()) } throws SocketTimeoutException()
        val result = repo.login("a@b.com", "p")
        assertTrue(result is UiState.Error)
        assertTrue((result as UiState.Error).message.contains("timeout"))
    }

    @Test
    fun `login generic exception returns generic error message`() = runTest {
        coEvery { api.login(any()) } throws RuntimeException("unexpected")
        val result = repo.login("a@b.com", "p")
        assertTrue(result is UiState.Error)
        assertFalse((result as UiState.Error).message.isBlank())
    }

    // --- register ---

    @Test
    fun `register success returns UiState Success with message`() = runTest {
        coEvery { api.register(any()) } returns ApiResponse("success", null, "Registrasi berhasil.")
        val result = repo.register("Janis", "12345", "janis@test.com", "pass123")
        assertTrue(result is UiState.Success)
        assertEquals("Registrasi berhasil.", (result as UiState.Success).data)
    }

    @Test
    fun `register duplicate email returns UiState Error`() = runTest {
        coEvery { api.register(any()) } returns ApiResponse("error", null, "Email sudah terdaftar.")
        val result = repo.register("Janis", "12345", "taken@test.com", "pass123")
        assertTrue(result is UiState.Error)
        assertEquals("Email sudah terdaftar.", (result as UiState.Error).message)
    }

    @Test
    fun `register network error returns friendly message`() = runTest {
        coEvery { api.register(any()) } throws UnknownHostException()
        val result = repo.register("Janis", "12345", "a@b.com", "pass")
        assertTrue(result is UiState.Error)
        assertTrue((result as UiState.Error).message.contains("internet"))
    }

    // --- logout ---

    @Test
    fun `logout success returns UiState Success`() = runTest {
        coEvery { api.logout(any()) } returns ApiResponse("success", null, "Logout berhasil.")
        val result = repo.logout("tok123")
        assertTrue(result is UiState.Success)
        assertEquals("Logout berhasil.", (result as UiState.Success).data)
    }

    @Test
    fun `logout invalid token returns UiState Error`() = runTest {
        coEvery { api.logout(any()) } returns ApiResponse("error", null, "Token tidak valid.")
        val result = repo.logout("badtoken")
        assertTrue(result is UiState.Error)
        assertEquals("Token tidak valid.", (result as UiState.Error).message)
    }

    @Test
    fun `logout passes Bearer prefix correctly`() = runTest {
        var capturedHeader = ""
        coEvery { api.logout(any()) } answers {
            capturedHeader = firstArg()
            ApiResponse("success", null, "OK")
        }
        repo.logout("tok123")
        assertEquals("Bearer tok123", capturedHeader)
    }
}
