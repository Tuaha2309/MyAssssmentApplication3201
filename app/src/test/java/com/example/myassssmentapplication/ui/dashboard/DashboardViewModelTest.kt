package com.example.myassssmentapplication.ui.dashboard

import com.example.myassssmentapplication.domain.model.FitnessEntity
import com.example.myassssmentapplication.domain.repo.FitnessRepositoryContract
import com.example.myassssmentapplication.ui.common.DashboardUiState
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
class DashboardViewModelTest {

    @Mock
    private lateinit var repository: FitnessRepositoryContract

    private lateinit var viewModel: DashboardViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val sampleEntities = listOf(
        FitnessEntity(
            exerciseName = "Squats",
            muscleGroup = "Legs",
            equipment = "Bodyweight or Barbell",
            difficulty = "Intermediate",
            caloriesBurnedPerHour = 500,
            description = "A compound exercise that primarily targets the quadriceps, hamstrings, and glutes."
        ),
        FitnessEntity(
            exerciseName = "Push-ups",
            muscleGroup = "Chest",
            equipment = "Bodyweight",
            difficulty = "Beginner",
            caloriesBurnedPerHour = 300,
            description = "A classic bodyweight exercise that targets the chest, shoulders, and triceps."
        ),
        FitnessEntity(
            exerciseName = "Deadlifts",
            muscleGroup = "Back",
            equipment = "Barbell",
            difficulty = "Advanced",
            caloriesBurnedPerHour = 450,
            description = "A compound exercise that targets multiple muscle groups, including the back, glutes, and hamstrings."
        ),
        FitnessEntity(
            exerciseName = "Plank",
            muscleGroup = "Core",
            equipment = "Bodyweight",
            difficulty = "Beginner",
            caloriesBurnedPerHour = 200,
            description = "An isometric core strength exercise that strengthens the abdominals, back, and shoulders."
        ),
        FitnessEntity(
            exerciseName = "Burpees",
            muscleGroup = "Full Body",
            equipment = "Bodyweight",
            difficulty = "Intermediate",
            caloriesBurnedPerHour = 600,
            description = "A full-body exercise that combines a squat, push-up, and jump, providing both strength and cardio benefits."
        ),
        FitnessEntity(
            exerciseName = "Bench Press",
            muscleGroup = "Chest",
            equipment = "Barbell or Dumbbells",
            difficulty = "Intermediate",
            caloriesBurnedPerHour = 350,
            description = "A compound upper-body exercise that targets the chest, shoulders, and triceps."
        ),
        FitnessEntity(
            exerciseName = "Mountain Climbers",
            muscleGroup = "Full Body",
            equipment = "Bodyweight",
            difficulty = "Beginner",
            caloriesBurnedPerHour = 400,
            description = "A dynamic exercise that engages multiple muscle groups and provides cardiovascular benefits."
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadFitnessEntities with valid keypass should emit success state`() = runTest {
        // Given
        val keypass = "fitness"
        whenever(repository.getFitnessEntities(keypass)).thenReturn(Result.success(sampleEntities))

        // When
        viewModel.loadFitnessEntities(keypass)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(DashboardUiState.Success::class.java)
        assertThat((state as DashboardUiState.Success).entities).isEqualTo(sampleEntities)
    }

    @Test
    fun `loadFitnessEntities with network error should emit error state`() = runTest {
        // Given
        val keypass = "fitness"
        val errorMessage = "Network error"
        whenever(repository.getFitnessEntities(keypass)).thenReturn(Result.failure(Exception(errorMessage)))

        // When
        viewModel.loadFitnessEntities(keypass)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(DashboardUiState.Error::class.java)
        assertThat((state as DashboardUiState.Error).message).isEqualTo(errorMessage)
    }

    @Test
    fun `loadFitnessEntities should emit loading state during request`() = runTest {
        // Given
        val keypass = "fitness"
        whenever(repository.getFitnessEntities(keypass)).thenReturn(Result.success(sampleEntities))

        // When
        viewModel.loadFitnessEntities(keypass)

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(DashboardUiState.Loading::class.java)
    }

    @Test
    fun `retry should call loadFitnessEntities again`() = runTest {
        // Given
        val keypass = "fitness"
        whenever(repository.getFitnessEntities(keypass)).thenReturn(Result.success(sampleEntities))

        // When
        viewModel.retry(keypass)
        advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(DashboardUiState.Success::class.java)
    }
}
