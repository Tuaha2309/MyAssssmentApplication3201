package com.example.myassssmentapplication

import com.example.myassssmentapplication.data.remote.api.AuthService
import com.example.myassssmentapplication.data.remote.api.DashboardService
import com.example.myassssmentapplication.data.remote.dto.AuthRequest
import com.example.myassssmentapplication.data.remote.dto.AuthResponse
import com.example.myassssmentapplication.data.remote.dto.DashboardResponse
import com.example.myassssmentapplication.data.remote.dto.FitnessEntityDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@ExperimentalCoroutinesApi
class ApiIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var authService: AuthService
    private lateinit var dashboardService: DashboardService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        authService = retrofit.create(AuthService::class.java)
        dashboardService = retrofit.create(DashboardService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `login API should return keypass`() = runTest {
        // Given
        val authResponse = AuthResponse("fitness")
        val responseBody = """
            {
                "keypass": "fitness"
            }
        """.trimIndent()
        
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        // When
        val request = AuthRequest("john", "12345678")
        val response = authService.login(request)

        // Then
        assertThat(response.keypass).isEqualTo("fitness")
    }

    @Test
    fun `dashboard API should return all 7 fitness entities`() = runTest {
        // Given
        val responseBody = """
            {
                "entities": [
                    {
                        "exerciseName": "Squats",
                        "muscleGroup": "Legs",
                        "equipment": "Bodyweight or Barbell",
                        "difficulty": "Intermediate",
                        "caloriesBurnedPerHour": 500,
                        "description": "A compound exercise that primarily targets the quadriceps, hamstrings, and glutes."
                    },
                    {
                        "exerciseName": "Push-ups",
                        "muscleGroup": "Chest",
                        "equipment": "Bodyweight",
                        "difficulty": "Beginner",
                        "caloriesBurnedPerHour": 300,
                        "description": "A classic bodyweight exercise that targets the chest, shoulders, and triceps."
                    },
                    {
                        "exerciseName": "Deadlifts",
                        "muscleGroup": "Back",
                        "equipment": "Barbell",
                        "difficulty": "Advanced",
                        "caloriesBurnedPerHour": 450,
                        "description": "A compound exercise that targets multiple muscle groups, including the back, glutes, and hamstrings."
                    },
                    {
                        "exerciseName": "Plank",
                        "muscleGroup": "Core",
                        "equipment": "Bodyweight",
                        "difficulty": "Beginner",
                        "caloriesBurnedPerHour": 200,
                        "description": "An isometric core strength exercise that strengthens the abdominals, back, and shoulders."
                    },
                    {
                        "exerciseName": "Burpees",
                        "muscleGroup": "Full Body",
                        "equipment": "Bodyweight",
                        "difficulty": "Intermediate",
                        "caloriesBurnedPerHour": 600,
                        "description": "A full-body exercise that combines a squat, push-up, and jump, providing both strength and cardio benefits."
                    },
                    {
                        "exerciseName": "Bench Press",
                        "muscleGroup": "Chest",
                        "equipment": "Barbell or Dumbbells",
                        "difficulty": "Intermediate",
                        "caloriesBurnedPerHour": 350,
                        "description": "A compound upper-body exercise that targets the chest, shoulders, and triceps."
                    },
                    {
                        "exerciseName": "Mountain Climbers",
                        "muscleGroup": "Full Body",
                        "equipment": "Bodyweight",
                        "difficulty": "Beginner",
                        "caloriesBurnedPerHour": 400,
                        "description": "A dynamic exercise that engages multiple muscle groups and provides cardiovascular benefits."
                    }
                ],
                "entityTotal": 7
            }
        """.trimIndent()
        
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
        )

        // When
        val response = dashboardService.getDashboard("fitness")

        // Then
        assertThat(response.entities).hasSize(7)
        assertThat(response.entityTotal).isEqualTo(7)
        assertThat(response.entities[0].exerciseName).isEqualTo("Squats")
        assertThat(response.entities[1].exerciseName).isEqualTo("Push-ups")
        assertThat(response.entities[2].exerciseName).isEqualTo("Deadlifts")
        assertThat(response.entities[3].exerciseName).isEqualTo("Plank")
        assertThat(response.entities[4].exerciseName).isEqualTo("Burpees")
        assertThat(response.entities[5].exerciseName).isEqualTo("Bench Press")
        assertThat(response.entities[6].exerciseName).isEqualTo("Mountain Climbers")
    }

    @Test
    fun `login followed by dashboard should work end-to-end`() = runTest {
        // Given - Login response
        val loginResponseBody = """
            {
                "keypass": "fitness"
            }
        """.trimIndent()
        
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(loginResponseBody)
        )

        // Given - Dashboard response
        val dashboardResponseBody = """
            {
                "entities": [
                    {
                        "exerciseName": "Squats",
                        "muscleGroup": "Legs",
                        "equipment": "Bodyweight or Barbell",
                        "difficulty": "Intermediate",
                        "caloriesBurnedPerHour": 500,
                        "description": "A compound exercise that primarily targets the quadriceps, hamstrings, and glutes."
                    },
                    {
                        "exerciseName": "Push-ups",
                        "muscleGroup": "Chest",
                        "equipment": "Bodyweight",
                        "difficulty": "Beginner",
                        "caloriesBurnedPerHour": 300,
                        "description": "A classic bodyweight exercise that targets the chest, shoulders, and triceps."
                    },
                    {
                        "exerciseName": "Deadlifts",
                        "muscleGroup": "Back",
                        "equipment": "Barbell",
                        "difficulty": "Advanced",
                        "caloriesBurnedPerHour": 450,
                        "description": "A compound exercise that targets multiple muscle groups, including the back, glutes, and hamstrings."
                    },
                    {
                        "exerciseName": "Plank",
                        "muscleGroup": "Core",
                        "equipment": "Bodyweight",
                        "difficulty": "Beginner",
                        "caloriesBurnedPerHour": 200,
                        "description": "An isometric core strength exercise that strengthens the abdominals, back, and shoulders."
                    },
                    {
                        "exerciseName": "Burpees",
                        "muscleGroup": "Full Body",
                        "equipment": "Bodyweight",
                        "difficulty": "Intermediate",
                        "caloriesBurnedPerHour": 600,
                        "description": "A full-body exercise that combines a squat, push-up, and jump, providing both strength and cardio benefits."
                    },
                    {
                        "exerciseName": "Bench Press",
                        "muscleGroup": "Chest",
                        "equipment": "Barbell or Dumbbells",
                        "difficulty": "Intermediate",
                        "caloriesBurnedPerHour": 350,
                        "description": "A compound upper-body exercise that targets the chest, shoulders, and triceps."
                    },
                    {
                        "exerciseName": "Mountain Climbers",
                        "muscleGroup": "Full Body",
                        "equipment": "Bodyweight",
                        "difficulty": "Beginner",
                        "caloriesBurnedPerHour": 400,
                        "description": "A dynamic exercise that engages multiple muscle groups and provides cardiovascular benefits."
                    }
                ],
                "entityTotal": 7
            }
        """.trimIndent()
        
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(dashboardResponseBody)
        )

        // When
        val loginRequest = AuthRequest("john", "12345678")
        val loginResponse = authService.login(loginRequest)
        val dashboardResponse = dashboardService.getDashboard(loginResponse.keypass)

        // Then
        assertThat(loginResponse.keypass).isEqualTo("fitness")
        assertThat(dashboardResponse.entities).hasSize(7)
        assertThat(dashboardResponse.entityTotal).isEqualTo(7)
        assertThat(dashboardResponse.entities[0].exerciseName).isEqualTo("Squats")
        assertThat(dashboardResponse.entities[6].exerciseName).isEqualTo("Mountain Climbers")
    }
}
