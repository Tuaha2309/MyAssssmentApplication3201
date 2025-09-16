package com.example.myassssmentapplication.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FitnessEntityDto(
    @Json(name = "exerciseName") val exerciseName: String,
    @Json(name = "muscleGroup") val muscleGroup: String,
    @Json(name = "equipment") val equipment: String,
    @Json(name = "difficulty") val difficulty: String,
    @Json(name = "caloriesBurnedPerHour") val caloriesBurnedPerHour: Int,
    @Json(name = "description") val description: String
)
