package com.example.myassssmentapplication.domain.repo

import com.example.myassssmentapplication.domain.model.FitnessEntity

interface FitnessRepositoryContract {
    suspend fun login(username: String, password: String): Result<String>
    suspend fun getFitnessEntities(keypass: String): Result<List<FitnessEntity>>
}
