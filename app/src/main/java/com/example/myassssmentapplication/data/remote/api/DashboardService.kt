package com.example.myassssmentapplication.data.remote.api

import com.example.myassssmentapplication.data.remote.dto.DashboardResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardService {
    @GET("/dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): DashboardResponse
}
