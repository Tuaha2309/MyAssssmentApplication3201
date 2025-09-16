package com.example.myassssmentapplication.ui.login

import com.example.myassssmentapplication.domain.repo.FitnessRepositoryContract
import com.example.myassssmentapplication.ui.common.LoginUiState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @Mock
    private lateinit var repository: FitnessRepositoryContract

    private lateinit var viewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with valid credentials should emit success state`() = runTest {
        // Given
        val username = "john"
        val password = "12345678"
        val keypass = "fitness"
        whenever(repository.login(username, password)).thenReturn(Result.success(keypass))

        // When
        viewModel.login(username, password)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(LoginUiState.Success::class.java)
        assertThat((state as LoginUiState.Success).keypass).isEqualTo(keypass)
    }

    @Test
    fun `login with invalid credentials should emit error state`() = runTest {
        // Given
        val username = "invalid"
        val password = "wrong"
        val errorMessage = "Invalid credentials"
        whenever(repository.login(username, password)).thenReturn(Result.failure(Exception(errorMessage)))

        // When
        viewModel.login(username, password)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(LoginUiState.Error::class.java)
        assertThat((state as LoginUiState.Error).message).isEqualTo(errorMessage)
    }

    @Test
    fun `login with empty credentials should emit error state`() = runTest {
        // When
        viewModel.login("", "")
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(LoginUiState.Error::class.java)
        assertThat((state as LoginUiState.Error).message).isEqualTo("Please enter both username and password")
    }

    @Test
    fun `login should emit loading state during request`() = runTest {
        // Given
        val username = "john"
        val password = "12345678"
        whenever(repository.login(username, password)).thenReturn(Result.success("fitness"))

        // When
        viewModel.login(username, password)

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(LoginUiState.Loading::class.java)
    }
}
