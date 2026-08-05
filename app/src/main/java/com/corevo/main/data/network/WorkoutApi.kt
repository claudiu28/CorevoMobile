package com.corevo.main.data.network

import com.corevo.main.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface WorkoutApi {
    @GET("workout-plans/exercises")
    suspend fun getExercises(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 100
    ): Response<PageType<Exercise>>

    @GET("workout-plans/user-plans")
    suspend fun getMyWorkoutPlans(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<PageType<WorkoutPlan>>

    @POST("workout-plans")
    suspend fun createWorkoutPlan(@Body request: CreateWorkoutPlanRequest): Response<WorkoutPlan>

    @POST("workout-plans/{planId}/send-for-validation")
    suspend fun sendForValidation(
        @Path("planId") planId: Long,
        @Body request: SendForValidationRequest
    ): Response<Unit>

    @GET("workout-plans/validators")
    suspend fun getValidators(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<PageType<ValidatorUser>>
}
