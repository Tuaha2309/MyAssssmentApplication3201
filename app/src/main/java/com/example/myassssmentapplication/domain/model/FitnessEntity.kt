package com.example.myassssmentapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FitnessEntity(
    val exerciseName: String,
    val muscleGroup: String,
    val equipment: String,
    val difficulty: String,
    val caloriesBurnedPerHour: Int,
    val description: String
) : Parcelable
