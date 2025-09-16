package com.example.myassssmentapplication.data.remote.api

import com.example.myassssmentapplication.data.remote.dto.AuthRequest
import com.example.myassssmentapplication.data.remote.dto.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/sydney/auth")
    suspend fun login(@Body request: AuthRequest): AuthResponse
}
