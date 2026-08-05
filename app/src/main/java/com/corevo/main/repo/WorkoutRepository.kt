package com.corevo.main.repo

import com.corevo.main.data.model.*
import com.corevo.main.data.network.WorkoutApi

class WorkoutRepository(private val workoutApi: WorkoutApi) {
    suspend fun getExercises(page: Int = 0): Result<PageType<Exercise>> {
        return try {
            val res = workoutApi.getExercises(page = page)
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to load exercises"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMyPlans(): Result<List<WorkoutPlan>> {
        return try {
            val res = workoutApi.getMyWorkoutPlans()
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!.content)
            else Result.failure(Exception("Failed to load workout plans"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createPlan(title: String, desc: String, diff: DifficultyLevel, weeks: Int, exIds: List<Long>): Result<WorkoutPlan> {
        return try {
            val req = CreateWorkoutPlanRequest(title, desc, diff, weeks, exIds)
            val res = workoutApi.createWorkoutPlan(req)
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!)
            else Result.failure(Exception("Failed to create plan"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendForValidation(planId: Long, validatorUsername: String): Result<Unit> {
        return try {
            val res = workoutApi.sendForValidation(planId, SendForValidationRequest(validatorUsername))
            if (res.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Failed to submit for validation"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getValidators(): Result<List<ValidatorUser>> {
        return try {
            val res = workoutApi.getValidators()
            if (res.isSuccessful && res.body() != null) Result.success(res.body()!!.content)
            else Result.failure(Exception("Failed to fetch coaches"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
