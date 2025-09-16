package com.example.myassssmentapplication.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myassssmentapplication.domain.model.FitnessEntity
import com.example.myassssmentapplication.ui.common.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Idle)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadFitnessEntities(keypass: String) {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            // Simulate loading - for demo purposes, return sample data
            kotlinx.coroutines.delay(1000) // Simulate network delay
            
            val sampleEntities = listOf(
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
            
            _uiState.value = DashboardUiState.Success(sampleEntities)
        }
    }

    fun retry(keypass: String) {
        loadFitnessEntities(keypass)
    }
}
