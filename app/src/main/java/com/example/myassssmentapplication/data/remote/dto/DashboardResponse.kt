package com.example.myassssmentapplication.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DashboardResponse(
    @Json(name = "entities") val entities: List<FitnessEntityDto>,
    @Json(name = "entityTotal") val entityTotal: Int
)
