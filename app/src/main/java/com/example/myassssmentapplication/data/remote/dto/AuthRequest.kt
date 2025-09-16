package com.example.myassssmentapplication.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthRequest(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String
)
