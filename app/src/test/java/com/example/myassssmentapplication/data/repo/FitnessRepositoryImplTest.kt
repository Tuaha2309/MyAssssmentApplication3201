package com.example.myassssmentapplication.data.repo

import com.example.myassssmentapplication.data.remote.api.AuthService
import com.example.myassssmentapplication.data.remote.api.DashboardService
import com.example.myassssmentapplication.data.remote.dto.AuthRequest
import com.example.myassssmentapplication.data.remote.dto.AuthResponse
import com.example.myassssmentapplication.data.remote.dto.DashboardResponse
import com.example.myassssmentapplication.data.remote.dto.FitnessEntityDto
import com.example.myassssmentapplication.domain.model.FitnessEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class FitnessRepositoryImplTest {

    @Mock
    private lateinit var authService: AuthService

    @Mock
    private lateinit var dashboardService: DashboardService

    private lateinit var repository: FitnessRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = FitnessRepositoryImpl(authService, dashboardService)
    }

    @Test
    fun `login with valid credentials should return success`() = runTest {
        // Given
        val username = "john"
        val password = "12345678"
        val keypass = "fitness"
        val authRequest = AuthRequest(username, password)
        val authResponse = AuthResponse(keypass)
        
        whenever(authService.login(authRequest)).thenReturn(authResponse)

        // When
        val result = repository.login(username, password)

        // Then
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(keypass)
    }

    @Test
    fun `login with invalid credentials should return failure`() = runTest {
        // Given
        val username = "invalid"
        val password = "wrong"
        val authRequest = AuthRequest(username, password)
        
        whenever(authService.login(authRequest)).thenThrow(
            HttpException(Response.error<AuthResponse>(401, "Unauthorized".toResponseBody("text/plain".toMediaType())))
        )

        // When
        val result = repository.login(username, password)

        // Then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `getFitnessEntities with valid keypass should return success`() = runTest {
        // Given
        val keypass = "fitness"
        val fitnessDtos = listOf(
            FitnessEntityDto(
                exerciseName = "Squats",
                muscleGroup = "Legs",
                equipment = "Bodyweight or Barbell",
                difficulty = "Intermediate",
                caloriesBurnedPerHour = 500,
                description = "A compound exercise that primarily targets the quadriceps, hamstrings, and glutes."
            )
        )
        val dashboardResponse = DashboardResponse(fitnessDtos, 1)
        
        whenever(dashboardService.getDashboard(keypass)).thenReturn(dashboardResponse)

        // When
        val result = repository.getFitnessEntities(keypass)

        // Then
        assertThat(result.isSuccess).isTrue()
        val entities = result.getOrNull()
        assertThat(entities).hasSize(1)
        assertThat(entities?.first()?.exerciseName).isEqualTo("Squats")
        assertThat(entities?.first()?.muscleGroup).isEqualTo("Legs")
    }

    @Test
    fun `getFitnessEntities with network error should return failure`() = runTest {
        // Given
        val keypass = "fitness"
        
        whenever(dashboardService.getDashboard(keypass)).thenThrow(
            HttpException(Response.error<DashboardResponse>(500, "Server Error".toResponseBody("text/plain".toMediaType())))
        )

        // When
        val result = repository.getFitnessEntities(keypass)

        // Then
        assertThat(result.isFailure).isTrue()
    }
}
